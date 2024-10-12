package agro.filelinkhub;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapperHelper {

  private final ObjectMapper objectMapper;

  @SneakyThrows
  public <T> List<T> parseList(String json, Class<T> clazz) {
    CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
    return objectMapper.readValue(json, type);
  }

}