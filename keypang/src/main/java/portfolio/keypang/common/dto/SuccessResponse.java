package portfolio.keypang.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse <T>{
  private int code;
  private String message;
  private T data;

}
