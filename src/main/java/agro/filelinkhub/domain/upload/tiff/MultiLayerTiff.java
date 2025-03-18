package agro.filelinkhub.domain.upload.tiff;

import agro.filelinkhub.domain.upload.File;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document(collection = "photos")
public class MultiLayerTiff implements File {

  @Id
  private String photoId;
  private final String type = "MultiLayerTiff";
  private final String contourId;
  private final Instant date;
  private final String extension;
  /**
   * List of layer names, which contains names such as red, green, nir etc.
   */
  private final List<Layer> layers;

  public MultiLayerTiff(
          String contourId,
          Instant date,
          String extension,
          List<Layer> layers
  ) {
    this.contourId = contourId;
    this.date = date;
    this.extension = extension;
    this.layers = layers;
    generateName();
  }

  public void generateName() {
    photoId = UUID.randomUUID().toString();
  }

  @Override
  public String name() {
    return photoId;
  }

  @Override
  public String bucket() {
    return "agro-photos";
  }

  @Override
  public String extension() {
    return extension;
  }

}
