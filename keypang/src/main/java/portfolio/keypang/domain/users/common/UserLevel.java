package portfolio.keypang.domain.users.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserLevel {
    UNAUTH_USER,USER,SELLER,ADMIN
}
