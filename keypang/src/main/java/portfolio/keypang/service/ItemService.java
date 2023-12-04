package portfolio.keypang.service;

import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.controller.dto.ItemDto.RegisterRequest;
import portfolio.keypang.domain.Item.Item;
import portfolio.keypang.domain.Item.ItemRepository;
import portfolio.keypang.domain.sellers.Seller;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final InternalService internalService;

  public String generateItemNum(Seller seller) {
    SecureRandom random = new SecureRandom();
    random.setSeed(System.currentTimeMillis());
    StringBuilder key = new StringBuilder(seller.getBrandName());
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
              request.getPrice(), request.getThumbNail(),
              request.getKeyboardType(), request.getWireType(),
              request.getWorkType(), request.getStock(), seller)
      );
    }
    itemRepository.save(
        Item.of(request.getName(), itemNum,
            request.getPrice(), request.getThumbNail(),
            request.getKeyboardType(), request.getWireType(),
            request.getWorkType(), request.getStock(), seller)
    );
  }

}