package agro.filelinkhub.configs;

import agro.filelinkhub.domain.ComponentsMap;
import agro.filelinkhub.domain.upload.File;
import agro.filelinkhub.domain.upload.PresignedUrlGenerator;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  public ComponentsMap<Class<? extends File>, PresignedUrlGenerator<? extends File>> urlGenerators(
          List<PresignedUrlGenerator<? extends File>> generators
  ) {
    return new ComponentsMap<>(generators, PresignedUrlGenerator::key);
  }

}
