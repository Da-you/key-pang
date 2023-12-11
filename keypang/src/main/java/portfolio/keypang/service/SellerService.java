package portfolio.keypang.service;

import static portfolio.keypang.controller.dto.SellerDto.SellerListResponse.*;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.common.dto.PageResponse;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.SellerDto.RegisterRequest;
import portfolio.keypang.controller.dto.SellerDto.SellerInfoResponse;
import portfolio.keypang.controller.dto.SellerDto.SellerListResponse;
import portfolio.keypang.controller.dto.SellerDto.UpdateSellerBusinessNumRequest;
import portfolio.keypang.controller.dto.SellerDto.UpdateSellerNameRequest;
import portfolio.keypang.domain.sellers.Seller;
import portfolio.keypang.domain.sellers.SellerRepository;
import portfolio.keypang.domain.users.common.UserLevel;
import portfolio.keypang.domain.users.user.User;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository sellerRepository;
  private final InternalService internalService;


  @Transactional
  public void register(String uniqueId, RegisterRequest request) {
    checkBusinessInfo(request);
    User user = internalService.getUserByUniqueId(uniqueId);
    user.updateLevel(UserLevel.SELLER);
    sellerRepository.save(
        Seller.of(request.getSellerName(),
            request.getBusinessNum(),
            user)
    );
  }

  @Transactional
  public void updateSellerName(String uniqueId, UpdateSellerNameRequest request) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    checkDuplicateSellerName(request);
    seller.updateSellerName(request.getSellerName());
  }

  @Transactional
  public void updateSellerNum(String uniqueId, UpdateSellerBusinessNumRequest request) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    checkDuplicateBusinessNumber(request);
    seller.updateSellerBusinessNum(request.getBusinessNum());
  }


  @Transactional
  public void deleteSellerLicense(String uniqueId) {
    Seller seller = internalService.getSellerByUniqueId(uniqueId);
    seller.deleteLicense("*,", "*");
    User user = seller.getUser();
    user.updateLevel(UserLevel.USER);
  }

  private void checkDuplicateSellerName(UpdateSellerNameRequest request) {
    if (sellerRepository.existsBySellerName(request.getSellerName())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BRAND_NAME);
    }
  }

  private void checkDuplicateBusinessNumber(UpdateSellerBusinessNumRequest request) {
    if (sellerRepository.existsByBusinessNum(request.getBusinessNum())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BUSINESS_NUMBER);
    }
  }

  private void checkBusinessInfo(RegisterRequest request) {
    if (sellerRepository.existsBySellerName(request.getSellerName())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BRAND_NAME);
    }
    if (sellerRepository.existsByBusinessNum(request.getBusinessNum())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BUSINESS_NUMBER);
    }
  }

  @Transactional(readOnly = true)
  public PageResponse<SellerListResponse> getSellerList() {
    Page<Seller> sellers = sellerRepository.findAll(PageRequest.of(0, 10));

    List<SellerListResponse> contents = sellers.getContent().stream()
        .map(seller -> builder()
            .sellerName(seller.getSellerName())
            .sellerInfo(SellerInfoResponse.builder()
                .userName(seller.getUser().getNickname())
                .businessNum(seller.getBusinessNum())
                .phone(seller.getUser().getPhone())
                .build()
            )
            .build())
        .collect(Collectors.toList());
    return PageResponse.of(sellers, contents);
  }

  @Transactional(readOnly = true)
  public SellerInfoResponse getSellerInfo(String sellerName) {
    Seller seller = sellerRepository.findBySellerName(sellerName);
    if (seller == null) {
      throw new GlobalException(ExceptionStatus.SELLER_NOT_FOUND);
    }
    return SellerInfoResponse.builder()
        .userName(seller.getUser().getNickname())
        .businessNum(seller.getBusinessNum())
        .phone(seller.getUser().getPhone())
        .build();
  }
}
