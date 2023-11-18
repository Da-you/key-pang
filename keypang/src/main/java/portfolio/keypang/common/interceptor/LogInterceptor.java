package portfolio.keypang.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import portfolio.keypang.service.InternalService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

  private static final String LOG_ID = "logId";


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String requestURI = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    request.setAttribute(LOG_ID, uuid);

    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
    }

    log.info("REQUEST [{}][{}][{}]", uuid, requestURI,
       handler);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    Object logId = (String) request.getAttribute(LOG_ID);
    log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
    if (ex != null) {
      log.error("afterCompletion error!!", ex);
    }
  }
}
