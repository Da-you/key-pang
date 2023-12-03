package portfolio.keypang.controller.dto;

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

    private String name;
    private Integer price;
    private String thumbNail;
    private KeyboardType keyboardType;
    private WireType wireType;
    private WorkType workType;
    private Integer stock;

  }
}
