package portfolio.keypang.domain.users.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({PARAMETER})
@Retention(RUNTIME)
public @interface CurrentUser {

}
