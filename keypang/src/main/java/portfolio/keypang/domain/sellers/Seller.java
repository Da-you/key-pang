package portfolio.keypang.domain.sellers;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import portfolio.keypang.domain.BaseTimeEntity;
import portfolio.keypang.domain.users.user.User;

/**
 * 판매자의 정보를 hard delete하기에는 부담이 되어 softDelete를 사용.
 */
@Entity
@Getter
@Where(clause = "deleted_at is null")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Seller extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String sellerName;

  @Column(nullable = false, unique = true)
  private String businessNum;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDateTime deletedAt;

  @Builder
  public static Seller of(String sellerName, String businessNum, User user) {
    return Seller.builder()
        .sellerName(sellerName)
        .businessNum(businessNum)
        .user(user)
        .build();
  }

  public void updateSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public void updateSellerBusinessNum(String businessNum) {
    this.businessNum = businessNum;
  }

  public void deleteLicense(String sellerName, String businessNum) {
    this.deletedAt = LocalDateTime.now();
    this.sellerName = sellerName;
    this.businessNum = businessNum;

  }
}
