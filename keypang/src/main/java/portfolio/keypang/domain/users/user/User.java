package portfolio.keypang.domain.users.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.users.common.AuthInfo;
import portfolio.keypang.domain.users.common.UserLevel;


@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuthInfo {

  @Column(unique = true, nullable = false)
  private String uniqueId;

  @Column(unique = true, nullable = false)
  private String nickname;

  private String password;
  @Column(nullable = false)
  private boolean phoneVerified = false;


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
  public void updateLevel(UserLevel userLevel) {
    this.userLevel = userLevel;
  }


  public void updatePhone(String phone) {
    this.phone = phone;
  }
}
