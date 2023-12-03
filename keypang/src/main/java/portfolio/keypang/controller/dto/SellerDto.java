package portfolio.keypang.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.sellers.Seller;

public class SellerDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class RegisterRequest {

    @NotBlank(message = "사업자 번호를 기재해 주세요")
    private String businessNum;
    @NotBlank(message = "브랜드명을 기재해 주세요")
    private String brandName;

  }

}
