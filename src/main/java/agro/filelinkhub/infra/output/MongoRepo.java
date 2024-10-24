package agro.filelinkhub.infra.output;

import agro.filelinkhub.domain.DocRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MongoRepo implements DocRepo {

  private final MongoTemplate mongoTemplate;

  @Override
  public void save(Object object) {
    mongoTemplate.save(object);
  }

}
