package portfolio.keypang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

  USER_NOT_FOUND(404, HttpStatus.NOT_FOUND,"user not found"),
  DUPLICATE_NICKNAME(409, HttpStatus.CONFLICT,"nickname is duplicate" ),;


  private final int code;
  private final HttpStatus status;
  private final String message;


}
