package portfolio.keypang.domain.sellers;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.BaseTimeEntity;
import portfolio.keypang.domain.users.user.User;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Seller extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String brandName;

  @Column(nullable = false, unique = true)
  private String businessNum;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;



}
