package portfolio.keypang.service;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.keypang.common.exception.ExceptionStatus;
import portfolio.keypang.common.exception.GlobalException;
import portfolio.keypang.common.properties.AppProperties;
import portfolio.keypang.controller.dto.UserDto.VerificationRequest;
import portfolio.keypang.domain.users.common.UserLevel;
import portfolio.keypang.domain.users.user.User;
import portfolio.keypang.utils.RedisUtil;

@Slf4j
@Service
@EnableConfigurationProperties(AppProperties.class)
public class SmsVerificationService {


  private final RedisUtil redisUtil;
  private final AppProperties appProperties;
  private final DefaultMessageService messageService;
  private final InternalService internalService;

  @Autowired
  public SmsVerificationService(RedisUtil redisUtil, AppProperties appProperties,
      InternalService internalService) {
    this.redisUtil = redisUtil;
    this.appProperties = appProperties;
    this.messageService = NurigoApp.INSTANCE.initialize(
        appProperties.getApi(), appProperties.getSecret(), "https://api.coolsms.co.kr");
    this.internalService = internalService;
  }

  public String createVerificationCode() {
    Random random = new Random();
    return String.valueOf(100000 + random.nextInt(900000));
  }

  public String createContent(String verificationCode) {
    return "[KeyPang] "
        + "인증번호는 [" + verificationCode + "] 입니다.";
  }

  @Transactional
  public void sendSms(String uniqueId, VerificationRequest request) {
    if (request.getPhone().isBlank()) {
      throw new GlobalException(ExceptionStatus.PHONE_NUMBER_IS_BLANK);
    }
    Message sms = new Message();
    sms.setFrom(appProperties.getFrom());
    sms.setTo(request.getPhone());
    sms.setType(MessageType.SMS);
    sms.setText(createContent(createVerificationCode()));
    String verificationCode = createVerificationCode();

    try {
      messageService.sendOne(
          new SingleMessageSendingRequest(sms)
      );
    } catch (Exception e) {
      log.error("문자 전송 실패");
    }
    redisUtil.createVerificationCode(request.getPhone(), verificationCode);
    log.info("인증번호: {}", verificationCode);
  }

  @Transactional
  public void verifyCode(String uniqueId, VerificationRequest request) {
    User user = internalService.getUserByUniqueId(uniqueId);
    if (isVerify(request)) {
      throw new GlobalException(ExceptionStatus.AUTHENTICATION_CODE_NOT_MATCH);
    }
    redisUtil.removeVerificationCode(request.getPhone());
    user.isPhoneVerified(true);
    user.updatePhone(request.getPhone());
    user.updateLevel(UserLevel.USER);
  }

  public boolean isVerify(VerificationRequest request) {
    return (!redisUtil.hasKey(request.getPhone()) &&
        redisUtil.getVerificationCode(request.getPhone()).equals(request.getVerificationCode()));
  }

}
