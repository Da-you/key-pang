package portfolio.keypang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

  private final ExceptionStatus status;

  public GlobalException(ExceptionStatus status) {
    super(status.getMessage());
    this.status = status;
  }

}
