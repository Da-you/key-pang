package portfolio.keypang.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("verification-sms")
public class AppProperties {

  private final String api;
  private final String secret;
  private final String from;

}
