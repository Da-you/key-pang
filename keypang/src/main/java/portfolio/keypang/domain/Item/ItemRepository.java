package portfolio.keypang.domain.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.keypang.domain.sellers.Seller;

public interface ItemRepository extends JpaRepository<Item, Long> {

  boolean existsByItemNum(String itemNum);

  Page<Item> findAllBySeller(Seller seller, Pageable pageable);
}
