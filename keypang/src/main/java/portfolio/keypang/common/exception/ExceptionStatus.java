package portfolio.keypang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

  USER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "user not found"),
  DUPLICATE_NICKNAME(409, HttpStatus.CONFLICT, "nickname is duplicate"),
  PHONE_NUMBER_IS_BLANK(400, HttpStatus.BAD_REQUEST, "phone number is blank"),
  AUTHENTICATION_CODE_NOT_MATCH(400, HttpStatus.BAD_REQUEST, "authentication code does not match"),
  DUPLICATE_BRAND_NAME(409, HttpStatus.CONFLICT, "brand name is duplicate"),
  DUPLICATE_BUSINESS_NUMBER(409, HttpStatus.CONFLICT, "business number is duplicate"),
  UNREGISTERED_SELLER(400, HttpStatus.BAD_REQUEST, "unregistered seller"),
  ITEM_NOT_FOUND(404, HttpStatus.NOT_FOUND, "item is not found"),
  UNAUTHORIZED_SELLER(401, HttpStatus.UNAUTHORIZED, "seller is unauthorized ");


  private final int code;
  private final HttpStatus status;
  private final String message;


}
