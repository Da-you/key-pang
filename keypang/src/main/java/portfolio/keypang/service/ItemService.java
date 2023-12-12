package portfolio.keypang.service;

import java.security.SecureRandom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.common.dto.PageResponse;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.ItemDto.ItemInfoResponse;
import portfolio.keypang.controller.dto.ItemDto.ItemListResponse;
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

  @Transactional(readOnly = true)
  public ItemInfoResponse getItemInfo(String uniqueId, Long itemId) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Item item = checkSellrItem(itemId, seller);
    return new ItemInfoResponse(item.getItemNum(), item.getName(), item.getPrice(),
        item.getThumbNail(), item.getKeyboardType(), item.getWireType(), item.getWorkType(),
        item.getStock());
  }

  @Transactional(readOnly = true)
  public PageResponse<ItemListResponse> getItemList(String uniqueId) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    Page<Item> items = itemRepository.findAllBySeller(seller, PageRequest.of(0, 10));

    List<ItemListResponse> contents = items.stream()
        .map(item -> new ItemListResponse(item.getItemNum(), item.getName(),
            item.getThumbNail()))
        .toList();
    return PageResponse.of(items, contents);
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

    if (isDeletedThumbnail(updateImage, item)) {
      String key = FileNameUtils.getFileName(item.getThumbNail());
      s3Service.deleteItemImage(key); // 기존 이미지 삭제
      item.deleteImage(); // DB에서 이미지 정보 삭제
    }
    if (updateImage != null) {
      item.updateThumbNail(imagePath(updateImage));
    }
  }

  private boolean isDeletedThumbnail(MultipartFile updateImage, Item item) {
    return (item.getThumbNail() != null && updateImage == null);
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
