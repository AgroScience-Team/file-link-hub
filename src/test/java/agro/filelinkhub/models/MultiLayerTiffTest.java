package agro.filelinkhub.models;

import java.time.LocalDate;
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
  private String fieldId;
  private LocalDate photoDate;
  private String photoExtension;
  /**
   * List of layer names, which contains names such as red, green, nir etc.
   */
  private List<String> layers;

}
