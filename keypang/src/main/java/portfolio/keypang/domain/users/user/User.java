package portfolio.keypang.domain.users.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import portfolio.keypang.domain.users.common.AuthInfo;
import portfolio.keypang.domain.users.common.UserLevel;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuthInfo {

  @Column(unique = true, nullable = false)
  private String uniqueId;

  @Column(unique = true, nullable = false)
  private String nickname;

  private String password;

  private boolean phoneVerified;


  @Builder
  public User(String username, String phone, UserLevel userLevel,
      String nickname, String password) {

    super(username, phone, userLevel);
    this.nickname = nickname;
    this.password = password;

  }

  public void generateUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public void isPhoneVerified(boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
  }


}
