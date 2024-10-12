package agro.filelinkhub.configs;

import agro.filelinkhub.configs.properties.S3Properties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class S3Conf {

  private final S3Properties s3Properties;

  @Bean
  public MinioClient s3Client() {
    log.info("Start creating s3 client: {}", s3Properties.toString());
    return MinioClient.builder()
            .endpoint(s3Properties.getUrl())
            .credentials(
                    s3Properties.getUser(),
                    s3Properties.getPassword()
            ).build();
  }

}
