package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.controller.dto.SellerDto.RegisterRequest;
import portfolio.keypang.domain.sellers.SellerRepository;
import portfolio.keypang.domain.users.common.annotation.CurrentUser;
import portfolio.keypang.service.SellerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sellers")
public class SellerApiController {

  private final SellerRepository sellerRepository;
  private final SellerService sellerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void registerSeller(@CurrentUser String uniqueId,
      @RequestBody @Valid RegisterRequest request) {
    sellerService.register(uniqueId, request);
  }


}
