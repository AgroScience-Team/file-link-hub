package agro.filelinkhub.configs;

import agro.filelinkhub.configs.properties.MongoProps;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class MongoConfig {

  private final MongoProps mongoProps;

  @Bean
  public MongoClient photosMongoClient() {
    var serverAddress = new ServerAddress(mongoProps.getHost(), mongoProps.getPort());

    var credential = MongoCredential.createCredential(
            mongoProps.getUser(),
            mongoProps.getDb(),
            mongoProps.getPassword().toCharArray()
    );

    var settings = MongoClientSettings.builder()
            .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(serverAddress)))
            .credential(credential)
            .build();

    return MongoClients.create(settings);
  }

  @Bean
  public MongoDatabaseFactory photosMongoDbFactory(MongoClient photosMongoClient) {
    return new SimpleMongoClientDatabaseFactory(photosMongoClient, mongoProps.getDb());
  }

  @Bean
  public MongoTemplate photosMongoTemplate(
          MongoDatabaseFactory photosMongoDbFactory,
          MappingMongoConverter mappingMongoConverter
  ) {
    mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return new MongoTemplate(photosMongoDbFactory, mappingMongoConverter);
  }

}