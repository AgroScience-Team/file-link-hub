package agro.filelinkhub.containers;

import static java.util.Objects.isNull;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

@Slf4j
public class MinioConfig {

  private static volatile MinIOContainer minioContainer = null;


  private static MinIOContainer getMinioContainer() {
    MinIOContainer instance = minioContainer;
    if (isNull(minioContainer)) {
      synchronized (MinIOContainer.class) {
        minioContainer = instance =
                new MinIOContainer("minio/minio:latest")
                        .withReuse(true)
                        .withLogConsumer(new Slf4jLogConsumer(log))
                        .withExposedPorts(9000)
                        .withUserName("minioadmin")
                        .withPassword("minioadmin")
                        .withStartupTimeout(Duration.ofSeconds(60));
        minioContainer.start();
      }
    }
    return instance;
  }

  @Component("MinioInitializer")
  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      var minioContainer = getMinioContainer();
      TestPropertyValues.of(
              "S3_URL: " + minioContainer.getS3URL(),
              "S3_USER: " + minioContainer.getUserName(),
              "S3_PASSWORD: " + minioContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}
