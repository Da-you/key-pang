package portfolio.keypang.domain.cart;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "cart" , orphanRemoval = true)
  private Set<CartItem> wishList = new HashSet<>();

  public void addCartItem(CartItem cartItem) {
    wishList.add(cartItem);
  }

}
