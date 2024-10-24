package agro.filelinkhub.containers;

import static java.util.Objects.isNull;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

@Slf4j
public class MongoConfig {

  private static volatile MongoDBContainer mongoDBContainer = null;

  private static MongoDBContainer getMongoDBContainer() {
    MongoDBContainer instance = mongoDBContainer;
    if (isNull(instance)) {
      synchronized (MongoDBContainer.class) {
        mongoDBContainer = instance =
                new MongoDBContainer("mongo:latest")
                        .withReuse(true)
                        .withLogConsumer(new Slf4jLogConsumer(log))
                        .withStartupTimeout(Duration.ofSeconds(60));
        mongoDBContainer.start();
      }
    }
    return instance;
  }

  @Component("MongoInitializer")
  public static class Initializer
          implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      var mongoDBContainer = getMongoDBContainer();
      TestPropertyValues.of(
              "MONGO_HOST: " + mongoDBContainer.getHost(),
              "MONGO_PORT: " + mongoDBContainer.getMappedPort(27017)
      ).applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}