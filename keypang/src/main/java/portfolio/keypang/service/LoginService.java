package portfolio.keypang.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.controller.dto.UserDto.LoginRequest;
import portfolio.keypang.domain.users.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

  private final HttpSession httpSession;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public void login(LoginRequest request) {
    // uniqueId, password 일치 확인
    existsByUniqueIdAndPassword(request);
    String session = request.getUniqueId();
    httpSession.setAttribute("user", session);
    log.info("user session: {}", httpSession.getAttribute("user"));
  }

  private void existsByUniqueIdAndPassword(LoginRequest request) {
    if (!userRepository.existsByUniqueIdAndPassword(request.getUniqueId(), request.getPassword())) {
      throw new GlobalException(ExceptionStatus.USER_NOT_FOUND);
    }
  }

  @Transactional
  public void logout() {
    HttpSession session = (HttpSession) httpSession.getAttribute("user");
    if (session != null) {
      session.invalidate();
    }
  }

}
