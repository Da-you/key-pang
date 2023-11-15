package portfolio.keypang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import portfolio.keypang.controller.dto.UserDto;
import portfolio.keypang.controller.dto.UserDto.VerificationRequest;
import portfolio.keypang.service.SmsVerificationService;
import portfolio.keypang.service.UserService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;
  private final SmsVerificationService smsVerificationService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public void userSave(@RequestBody @Valid UserDto.UserSaveRequest request) {
    userService.save(request);
  }

  @PostMapping("/phones/send-verification")
  @ResponseStatus(HttpStatus.CREATED)
  public void sendSms(@RequestBody VerificationRequest request) {
    smsVerificationService.sendSms(request.getPhone());
  }

  @PostMapping("/phones/verify")
  @ResponseStatus(HttpStatus.OK)
  public void verifySms(@RequestBody VerificationRequest request) {
    smsVerificationService.verifyCode(request);
  }
}
