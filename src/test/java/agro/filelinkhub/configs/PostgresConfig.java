package agro.filelinkhub.configs;

import static java.util.Objects.isNull;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

@Slf4j
public class PostgresConfig {
    private static volatile PostgreSQLContainer<?> postgreSQLContainer = null;


    private static PostgreSQLContainer<?> getPostgreSQLContainer(){
        PostgreSQLContainer<?> instance = postgreSQLContainer;
        if(isNull(instance)){
            synchronized (PostgreSQLContainer.class){
                postgreSQLContainer = instance =
                        new PostgreSQLContainer<>("postgres:15.1")
                                .withDatabaseName("public")
                                .withUsername("postgres")
                                .withPassword("postgres")
                                .withInitScript("init.sql")
                                .withReuse(true)
                                .withLogConsumer(new Slf4jLogConsumer(log))
                                .withStartupTimeout(Duration.ofSeconds(60));
                postgreSQLContainer.start();
            }
        }
        return instance;
    }

    @Component("PostgresInitializer")
    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            var postgreSQLContainer = getPostgreSQLContainer();
            TestPropertyValues.of(
                    "HOST_DB: " + postgreSQLContainer.getHost(),
                    "PORT_DB: " + postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                    "POSTGRES_USER: " + postgreSQLContainer.getUsername(),
                    "POSTGRES_PASSWORD: " + postgreSQLContainer.getPassword(),
                    "POSTGRES_DB: " + postgreSQLContainer.getDatabaseName()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
