package portfolio.keypang.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.ItemDto.ItemStockRequest;
import portfolio.keypang.controller.dto.ItemDto.RegisterRequest;
import portfolio.keypang.domain.Item.Item;
import portfolio.keypang.domain.Item.ItemRepository;
import portfolio.keypang.domain.sellers.Seller;
import portfolio.keypang.service.s3.AwsS3Service;
import portfolio.keypang.service.s3.utils.FileNameUtils;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final InternalService internalService;
  private final AwsS3Service s3Service;

  public String generateItemNum(Seller seller) {
    SecureRandom random = new SecureRandom();
    random.setSeed(System.currentTimeMillis());
    StringBuilder key = new StringBuilder(seller.getSellerName());
    for (int i = 0; i < 6; i++) {
      key.append(random.nextInt(10));
    }
    return key.toString();
  }

  @Transactional
  public void register(String uniqueId, RegisterRequest request, MultipartFile imagePath) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    String itemNum = generateItemNum(seller);

    if (itemRepository.existsByItemNum(itemNum)) {
      String retryNum = itemNum + "R";
      itemRepository.save(
          Item.of(request.getName(), retryNum,
              request.getPrice(), imagePath(imagePath), request.getKeyboardType(),
              request.getWireType(), request.getWorkType(),
              request.getStock(), seller)
      );
    }
    itemRepository.save(
        Item.of(request.getName(), itemNum,
            request.getPrice(), imagePath(imagePath),
            request.getKeyboardType(), request.getWireType(),
            request.getWorkType(), request.getStock(), seller)
    );
  }

  @Transactional
  public void unActiveItem(String uniqueId, Long itemId) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Item item = checkSellrItem(itemId, seller);
    item.unActive();
  }

  @Transactional
  public void updateInfo(String uniqueId, Long itemId, RegisterRequest request) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Item item = checkSellrItem(itemId, seller);
    item.updateItem(request.getName(), request.getPrice(),
        request.getKeyboardType(), request.getWireType(), request.getWorkType());
  }

  @Transactional
  public void addStock(String uniqueId, Long itemId, ItemStockRequest request) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Item item = checkSellrItem(itemId, seller);
    item.addItemStock(request.getStock());

  }

  @Transactional
  public void updateImage(String uniqueId, Long itemId, MultipartFile updateImage) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Item item = checkSellrItem(itemId, seller);

    if (item.getThumbNail() != null && updateImage == null || item.getThumbNail() != null) {
      String key = FileNameUtils.getFileName(item.getThumbNail());
      s3Service.deleteItemImage(key);
      item.deleteImage();
    }
  }


  private Item checkSellrItem(Long itemId, Seller seller) {
    Item item = itemRepository.findById(itemId).orElseThrow(() -> new GlobalException(
        ExceptionStatus.ITEM_NOT_FOUND));
    if (!item.getSeller().equals(seller)) {
      throw new GlobalException(ExceptionStatus.UNAUTHORIZED_SELLER);
    }
    return item;
  }

  private String imagePath(MultipartFile imagePath) {
    if (imagePath == null) {
      return null;
    }
    return s3Service.uploadItemImage(imagePath);
  }

}
