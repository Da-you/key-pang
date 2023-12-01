package portfolio.keypang.domain.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.BaseTimeEntity;
import portfolio.keypang.domain.Item.common.KeyboardType;
import portfolio.keypang.domain.Item.common.WireType;
import portfolio.keypang.domain.Item.common.WorkType;
import portfolio.keypang.domain.sellers.Seller;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private Integer price;

  private String thumbNail;

  @Enumerated(EnumType.STRING)
  private KeyboardType keyboardType;

  @Enumerated(EnumType.STRING)
  private WireType wireType;

  @Enumerated(EnumType.STRING)
  private WorkType workType;
  private Integer stock;

  @ManyToOne(optional = false)
  @JoinColumn(name = "seller_id")
  private Seller seller;

}
