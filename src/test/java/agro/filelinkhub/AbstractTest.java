package agro.filelinkhub;

import agro.filelinkhub.containers.MinioConfig;
import agro.filelinkhub.containers.MongoConfig;
import agro.filelinkhub.containers.PostgresConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {
        MinioConfig.Initializer.class,
        PostgresConfig.Initializer.class,
        MongoConfig.Initializer.class
})
@TestPropertySource(locations = {"classpath:application.yaml"})
@ActiveProfiles("test")
public abstract class AbstractTest {

}