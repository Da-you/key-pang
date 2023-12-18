package portfolio.keypang.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.domain.Item.ItemRepository;
import portfolio.keypang.domain.sellers.Seller;
import portfolio.keypang.domain.sellers.SellerRepository;
import portfolio.keypang.domain.users.user.User;
import portfolio.keypang.domain.users.user.UserRepository;

@Service
@RequiredArgsConstructor
public class InternalService {

  private final UserRepository userRepository;
  private final SellerRepository sellerRepository;
  private final ItemRepository itemRepository;
  private final HttpSession httpSession;


  public User getUserByUniqueId(String uniqueId) {
    if (!userRepository.existsByUniqueId(uniqueId)) {
      throw new GlobalException(ExceptionStatus.USER_NOT_FOUND);
    }
    return userRepository.findByUniqueId(uniqueId);

  }

  public User getUniqueIdBySession() {
    String uniqueId = (String) httpSession.getAttribute("user");
    return userRepository.findByUniqueId(uniqueId);
  }

  public Seller getSellerByUniqueId(String uniqueId) {
    User user = getUserByUniqueId(uniqueId);
    if (sellerRepository.existsByUser(user)) {
      throw new GlobalException(ExceptionStatus.UNREGISTERED_SELLER);
    }
    return sellerRepository.findByUser(user);
  }
  public Seller getSellerByName(String sellerName) {
    if (!sellerRepository.existsBySellerName(sellerName)) {
      throw new GlobalException(ExceptionStatus.SELLER_NOT_FOUND);
    }
    return sellerRepository.findBySellerName(sellerName);
  }


}
