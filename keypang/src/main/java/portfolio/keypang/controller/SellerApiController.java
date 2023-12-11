package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import portfolio.keypang.common.dto.PageResponse;
import portfolio.keypang.common.dto.SuccessResponse;
import portfolio.keypang.controller.dto.SellerDto.RegisterRequest;
import portfolio.keypang.controller.dto.SellerDto.SellerInfoResponse;
import portfolio.keypang.controller.dto.SellerDto.SellerListResponse;
import portfolio.keypang.controller.dto.SellerDto.UpdateSellerBusinessNumRequest;
import portfolio.keypang.controller.dto.SellerDto.UpdateSellerNameRequest;
import portfolio.keypang.domain.users.common.annotation.CurrentUser;
import portfolio.keypang.service.SellerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sellers")
public class SellerApiController {

  private final SellerService sellerService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public SuccessResponse<PageResponse<SellerListResponse>> getSellerList() {
    return new SuccessResponse<>(sellerService.getSellerList());
  }

  @GetMapping("/{sellerName}")
  @ResponseStatus(HttpStatus.OK)
  public SuccessResponse<SellerInfoResponse> getSellerInfo(@PathVariable String sellerName) {
    return new SuccessResponse<>(sellerService.getSellerInfo(sellerName));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void registerSeller(@CurrentUser String uniqueId,
      @RequestBody @Valid RegisterRequest request) {
    sellerService.register(uniqueId, request);
  }

  @PatchMapping("/name")
  @ResponseStatus(HttpStatus.OK)
  public void updateSellerName(@CurrentUser String uniqueId,
      @RequestBody @Valid UpdateSellerNameRequest request) {
    sellerService.updateSellerName(uniqueId, request);
  }

  @PatchMapping("/business-num")
  @ResponseStatus(HttpStatus.OK)
  public void updateSellerBusinessNum(@CurrentUser String uniqueId,
      @RequestBody @Valid UpdateSellerBusinessNumRequest request) {
    sellerService.updateSellerNum(uniqueId, request);
  }

  @DeleteMapping("/license")
  @ResponseStatus(HttpStatus.OK)
  public void deleteSellerLicense(@CurrentUser String uniqueId) {
    sellerService.deleteSellerLicense(uniqueId);
  }

}
