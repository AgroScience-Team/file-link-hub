package agro.filelinkhub;

import agro.filelinkhub.configs.MinioConfig;
import agro.filelinkhub.configs.PostgresConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {MinioConfig.Initializer.class, PostgresConfig.Initializer.class})
@TestPropertySource(locations = {"classpath:application.yaml"})
public abstract class AbstractTest {

}