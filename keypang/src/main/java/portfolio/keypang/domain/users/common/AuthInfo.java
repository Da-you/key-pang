package portfolio.keypang.domain.users.common;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.keypang.domain.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.*;

@Entity
@Getter
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    protected Long id;

    protected String username;

    protected String phone;

    @Enumerated(EnumType.STRING)
    protected UserLevel userLevel;

    public AuthInfo(String username, String phone, UserLevel userLevel) {
        this.username = username;
        this.phone = phone;
        this.userLevel = userLevel;
    }
}
