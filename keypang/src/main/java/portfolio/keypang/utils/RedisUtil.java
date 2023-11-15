package portfolio.keypang.utils;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtil {

  private final String PREFIX = "sms:%s";
  private final String VERIFICATION_CODE = "verificationCode:%s";
  private final int LIMIT_TIME = 3 * 60;

  private final RedisTemplate<String, String> redisTemplate;


  public void createVerificationCode(String phone, String code) {
    String key = String.format(PREFIX, phone);
    String value = String.format(VERIFICATION_CODE, code);
    redisTemplate.opsForValue()
        .set(key, value, Duration.ofSeconds(LIMIT_TIME));
  }

  public String getVerificationCode(String phone) {
    String key = String.format(PREFIX, phone);
    return redisTemplate.opsForValue().get(key);
  }

  public void removeVerificationCode(String phone) {
    redisTemplate.delete(PREFIX + phone);
  }

  public boolean hasKey(String phone) {
    return redisTemplate.hasKey(PREFIX + phone);
  }

}
