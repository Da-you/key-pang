package portfolio.keypang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.SellerDto.RegisterRequest;
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
        Seller.of(request.getBrandName(),
            request.getBusinessNum(),
            user)
    );
  }

  private void checkBusinessInfo(RegisterRequest request) {
    if (sellerRepository.existsByBrandName(request.getBrandName())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BRAND_NAME);
    }
    if (sellerRepository.existsByBusinessNum(request.getBusinessNum())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_BUSINESS_NUMBER);
    }
  }

}
