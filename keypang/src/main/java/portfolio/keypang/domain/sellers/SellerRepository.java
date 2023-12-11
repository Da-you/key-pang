package portfolio.keypang.domain.sellers;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.keypang.domain.users.user.User;

public interface SellerRepository extends JpaRepository<Seller,Long> {

  boolean existsBySellerName(String sellerName);

  boolean existsByBusinessNum(String businessNum);

  boolean existsByUser(User user);

  Seller findByUser(User user);

  Seller findBySellerName(String sellerName);
}
