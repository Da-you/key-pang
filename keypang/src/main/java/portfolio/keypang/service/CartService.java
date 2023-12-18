package portfolio.keypang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.keypang.domain.Item.ItemRepository;
import portfolio.keypang.domain.cart.CartRepository;
import portfolio.keypang.domain.users.user.User;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final InternalService internalService;
  private final ItemRepository itemRepository;

  public void addCart(String uniqueId, String itemNum) {
    User user = internalService.getUserByUniqueId(uniqueId);

  }
}
