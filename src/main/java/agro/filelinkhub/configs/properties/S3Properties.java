package agro.filelinkhub.configs.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "s3")
@NoArgsConstructor
public class S3Properties {

  private String url;
  private String user;
  private String password;

}
