package portfolio.keypang.utils;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtil {

  private final String PREFIX = "sms:";
  private final int LIMIT_TIME = 3 * 60;

  private final RedisTemplate<String, String> redisTemplate;


  public void createVerificationCode(String phone, String code) {
    redisTemplate.opsForValue()
        .set(PREFIX + phone, code, Duration.ofSeconds(LIMIT_TIME));
  }

  public String getVerificationCode(String phone) {
    return redisTemplate.opsForValue().get(PREFIX + phone);
  }

  public void removeVerificationCode(String phone) {
    redisTemplate.delete(PREFIX + phone);
  }

  public void hasKey(String phone) {
    redisTemplate.hasKey(PREFIX + phone);
  }

}
