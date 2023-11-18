package portfolio.keypang.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import portfolio.keypang.domain.users.common.UserLevel;
import portfolio.keypang.domain.users.common.utils.encrytion.EncryptionService;
import portfolio.keypang.domain.users.user.User;

/*
 * DTO의 관리를 위해 inner class로 생성한다.
 */

public class UserDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UserSaveRequest {

    @NotBlank(message = "username은 필수 입력 값입니다.")
    private String username;

//    @NotBlank(message = "phone은 필수 입력 값입니다.")
//    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "phone은 010으로 시작하는 11자리 숫자입니다.")
//    private String phone;

    @NotBlank(message = "nickname는 필수 입력 값입니다.")
    @Size(min = 2, max = 10, message = "nickname은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "password는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$", message = "password는 영문, 숫자를 포함한 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @Builder
    public UserSaveRequest(String username, String nickname, String password) {
      this.username = username;
      this.nickname = nickname;
      this.password = password;
    }

    public void passwordEncode(EncryptionService encryptionService) {
      this.password = encryptionService.encrypt(password);
    }

    public User toEntity() {

      return User.builder()
          .username(username)
          .nickname(nickname)
          .password(password)
          .userLevel(UserLevel.UNAUTH_USER)
          .build();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class VerificationRequest {

    private String phone;

    private String verificationCode;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class LoginRequest {

    @NotBlank(message = "uniqueId는 필수 입력 값입니다.")
    private String uniqueId;
    @NotBlank(message = "password는 필수 입력 값입니다.")
    private String password;

    public void passwordEncode(EncryptionService encryptionService) {
      this.password = encryptionService.encrypt(password);
    }
  }

}
