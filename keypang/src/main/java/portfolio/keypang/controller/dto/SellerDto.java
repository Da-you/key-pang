package portfolio.keypang.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SellerListResponse {

    private String sellerName;
    private String businessNum;
    private String phone;
    // TODO:  List<Item> items;
  }

}
