package portfolio.keypang.domain.users.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.BaseTimeEntity;
import portfolio.keypang.domain.users.common.AuthInfo;
import portfolio.keypang.domain.users.common.UserLevel;

import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuthInfo {

    @Column(unique = true, nullable = false)
    private String uniqueId;
    @Column(unique = true, nullable = false)
    private String nickname;
    private String password;
    private boolean phoneVerified;



    public User(String username, String phone, UserLevel userLevel, String uniqueId,
                String nickname, String password, boolean phoneVerified) {

        super(username, phone, userLevel);
        this.uniqueId = uniqueId;
        this.nickname = nickname;
        this.password = password;
        this.phoneVerified = phoneVerified;

    }



}
