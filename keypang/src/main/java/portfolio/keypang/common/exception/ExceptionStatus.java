package portfolio.keypang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

  USER_NOT_FOUND(404, HttpStatus.NOT_FOUND,"user not found"),
  DUPLICATE_NICKNAME(409, HttpStatus.CONFLICT,"nickname is duplicate" ),
  PHONE_NUMBER_IS_BLANK(400,HttpStatus.BAD_REQUEST,"phone number is blank"),
  AUTHENTICATION_CODE_NOT_MATCH(400,HttpStatus.BAD_REQUEST ,"authentication code does not match" );


  private final int code;
  private final HttpStatus status;
  private final String message;


}
