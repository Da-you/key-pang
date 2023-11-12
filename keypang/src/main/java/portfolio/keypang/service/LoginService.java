package portfolio.keypang.service;

import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.controller.dto.UserDto.UserSaveRequest.LoginRequest;
import portfolio.keypang.domain.users.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

  private final HttpSession httpSession;
  private final UserRepository userRepository;

  @Transactional
  public void login(LoginRequest request) {
    // phone, password 일치 확인
    userRepository.existsByPhoneAndPassword(request.getPhone(), request.getPassword());
    String session = UUID.randomUUID().toString();
    httpSession.setAttribute("user", session);
    log.info("user session: {}", httpSession.getAttribute("user"));
  }

  @Transactional
  public void logout() {
    HttpSession session = (HttpSession) httpSession.getAttribute("user");
    if (session != null) {
      session.invalidate();
    }
  }

}
