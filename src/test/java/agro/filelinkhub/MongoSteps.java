package agro.filelinkhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public abstract class MongoSteps extends HttpSteps {

  @Autowired
  private MongoTemplate mongoTemplate;

  protected  <T> T findById(String id, Class<T> clazz) {
    Query query = new Query(Criteria.where("_id").is(id));
    return mongoTemplate.findOne(query, clazz);
  }

}
