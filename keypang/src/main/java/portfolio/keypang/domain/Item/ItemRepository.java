package portfolio.keypang.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  boolean existsByItemNum(String itemNum);

}
