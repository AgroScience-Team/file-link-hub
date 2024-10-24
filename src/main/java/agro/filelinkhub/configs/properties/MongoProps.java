package agro.filelinkhub.configs.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "mongo")
@NoArgsConstructor
public class MongoProps {

  private String user;
  private String password;
  private String host;
  private Integer port;
  private String db;

}