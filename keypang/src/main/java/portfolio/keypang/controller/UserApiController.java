package portfolio.keypang.controller;

import static portfolio.keypang.controller.dto.UserDto.*;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import portfolio.keypang.common.dto.SuccessResponse;
import portfolio.keypang.controller.dto.UserDto;
import portfolio.keypang.controller.dto.UserDto.LoginRequest;
import portfolio.keypang.controller.dto.UserDto.VerificationRequest;
import portfolio.keypang.domain.users.common.annotation.CurrentUser;
import portfolio.keypang.domain.users.user.User;
import portfolio.keypang.service.LoginService;
import portfolio.keypang.service.SmsVerificationService;
import portfolio.keypang.service.UserService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;
  private final LoginService loginService;
  private final SmsVerificationService smsVerificationService;

  @GetMapping("/duplicate/nickname")
  @ResponseStatus(HttpStatus.OK)
  public SuccessResponse<Boolean> duplicateNickname(@PathVariable String nickname) {
   return new SuccessResponse<>(userService.existsByNickname(nickname));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void userSave(@RequestBody @Valid UserSaveRequest request) {
    userService.save(request);
  }
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public void login(@RequestBody @Valid LoginRequest request) {
    loginService.login(request);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logout(@CurrentUser String uniqueId){
    loginService.logout();
  }

  @PostMapping("/phones/send-verification")
  @ResponseStatus(HttpStatus.CREATED)
  public void sendSms(@CurrentUser String uniqueId, @RequestBody VerificationRequest request) {
    smsVerificationService.sendSms(uniqueId,request);
  }

  @PostMapping("/phones/verify")
  @ResponseStatus(HttpStatus.OK)
  public void verifySms(@CurrentUser String uniqueId,@RequestBody VerificationRequest request) {
    smsVerificationService.verifyCode(uniqueId,request);
  }
}
