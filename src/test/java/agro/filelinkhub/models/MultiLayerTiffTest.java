package agro.filelinkhub.models;

import java.time.Instant;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "photos")
public class MultiLayerTiffTest {

  @Id
  private String photoId;
  private String type;
  private String contourId;
  private Instant date;
  private String extension;
  private List<String> layers;

}
