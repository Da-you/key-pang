package portfolio.keypang.service;

import java.util.HashMap;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import portfolio.keypang.common.properties.AppProperties;
import portfolio.keypang.utils.RedisUtil;

@Slf4j
@Service
@EnableConfigurationProperties(AppProperties.class)
public class SmsVerificationService {


  private final RedisUtil redisUtil;
  private final AppProperties appProperties;
  private final DefaultMessageService messageService;

  @Autowired
  public SmsVerificationService(RedisUtil redisUtil, AppProperties appProperties
  ) {
    this.redisUtil = redisUtil;
    this.appProperties = appProperties;
    this.messageService = NurigoApp.INSTANCE.initialize(
        appProperties.getApi(), appProperties.getSecret(), "https://api.coolsms.co.kr");
  }

  public String createVerificationCode() {
    Random random = new Random();
    return String.valueOf(100000 + random.nextInt(900000));
  }

  public String createContent(String verificationCode) {
    return "[KeyPang] "
        + "인증번호는 [" + verificationCode + "] 입니다.";
  }


  public void sendSms(String phone) {
    Message sms = new Message();
    sms.setFrom(appProperties.getFrom());
    sms.setTo(phone);
    sms.setType(MessageType.SMS);
    sms.setText(createContent(createVerificationCode()));
    String verificationCode = createVerificationCode();

    try {
      SingleMessageSentResponse result = messageService.sendOne(
          new SingleMessageSendingRequest(sms));
    } catch (Exception e) {
      log.error("문자 전송 실패");
    }
    redisUtil.createVerificationCode(phone, verificationCode);
    log.info("인증번호: {}", verificationCode);
  }

  public boolean verifyCode() {
    return true;
  }

}
