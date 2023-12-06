package portfolio.keypang.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.Item.common.KeyboardType;
import portfolio.keypang.domain.Item.common.WireType;
import portfolio.keypang.domain.Item.common.WorkType;

public class ItemDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class RegisterRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;
    private Integer price;
    @NotBlank(message = "키보드 타입을 입력해주세요.")
    private KeyboardType keyboardType;
    @NotBlank(message = "와이어 타입을 입력해주세요.")
    private WireType wireType;
    @NotBlank(message = "동작 타입을 입력해주세요.")
    private WorkType workType;
    @NotBlank(message = "재고를 입력해주세요.")
    @Min(value = 1, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ItemStockRequest {

      @NotBlank(message = "재고를 입력해주세요.")
      @Min(value = 1, message = "재고는 0 이상이어야 합니다.")
      private Integer stock;
  }
}
