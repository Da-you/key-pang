package portfolio.keypang.domain.users.common.utils.encrytion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class SHA256EncryptionService implements EncryptionService {

  @Override
  public String encrypt(String s) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(s.getBytes());
      byte[] byteData = md.digest();
      StringBuilder sb = new StringBuilder();
      for (byte byteDatum : byteData) {
        sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
