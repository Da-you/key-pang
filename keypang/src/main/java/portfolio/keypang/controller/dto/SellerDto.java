package portfolio.keypang.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.Item.Item;

public class SellerDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class RegisterRequest {

    @NotBlank(message = "사업자 번호를 기재해 주세요")
    private String businessNum;
    @NotBlank(message = "브랜드명을 기재해 주세요")
    private String sellerName;

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateSellerNameRequest {

    @NotBlank(message = "브랜드명을 기재해 주세요")
    private String sellerName;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UpdateSellerBusinessNumRequest {

    @NotBlank(message = "사업자 번호를 기재해 주세요")
    private String businessNum;
  }


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SellerListResponse {

    private String sellerName;
    private SellerInfoResponse sellerInfo;

  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SellerInfoResponse {
    private String userName;
    private String businessNum;
    private String phone;
    // TODO : item list 추가
  }

}
