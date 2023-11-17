package portfolio.keypang.service;

import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.UserDto.UserSaveRequest;
import portfolio.keypang.domain.users.user.User;
import portfolio.keypang.domain.users.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final String PREFIX = "KP";
  private static final int RANDOM_NUM_LENGTH = 6;

  private final UserRepository userRepository;

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
    if (userRepository.existsByNickname(request.getNickname())) {
      throw new GlobalException(ExceptionStatus.DUPLICATE_NICKNAME); // 별도의 에러 처리 필요
    }
    // 인증 처리 필요

    User user = request.toEntity();

    // 유니크 아이디 생성
    user.generateUniqueId(generateUniqueId());

    userRepository.save(user);
  }
}
