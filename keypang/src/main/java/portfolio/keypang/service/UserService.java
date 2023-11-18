package portfolio.keypang.service;

import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.UserDto.UserSaveRequest;
import portfolio.keypang.domain.users.common.utils.encrytion.EncryptionService;
import portfolio.keypang.domain.users.user.User;
import portfolio.keypang.domain.users.user.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private static final String PREFIX = "KP";
  private static final int RANDOM_NUM_LENGTH = 6;

  private final UserRepository userRepository;
  private final EncryptionService encryptionService;

  public String generateUniqueId() {
    SecureRandom random = new SecureRandom();
    random.setSeed(System.currentTimeMillis());
    StringBuilder key = new StringBuilder(PREFIX);
    for (int i = 0; i < RANDOM_NUM_LENGTH; i++) {
      key.append(random.nextInt(10));
    }
    return key.toString();
  }

  @Transactional
  public void save(UserSaveRequest request) {
    // 닉네임 중복 확인
    if (existsByNickname(request.getNickname())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_NICKNAME);
    }
    log.info("rewrite password: {}", request.getPassword());

    request.passwordEncode(encryptionService);

    User user = request.toEntity();
    log.info("encode password: {}", user.getPassword());

    // 유니크 아이디 생성
    user.generateUniqueId(generateUniqueId());

    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public boolean existsByNickname(String nickname) {
    return userRepository.existsByNickname(nickname);
  }
}
