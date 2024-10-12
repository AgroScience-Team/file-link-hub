package agro.filelinkhub.domain.upload.tiff;

import agro.filelinkhub.domain.upload.File;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MultiLayerTiff implements File {

  private final String type = "MultiLayerTiff";
  private final String fieldId;
  private final LocalDate photoDate;
  private final String photoExtension;
  /**
   * List of layer names, which contains names such as red, green, nir etc.
   */
  private final List<String> layers;
  private String photoId;

  public MultiLayerTiff(String fieldId, LocalDate photoDate, String photoExtension, List<String> layers) {
    this.fieldId = fieldId;
    this.photoDate = photoDate;
    this.photoExtension = photoExtension;
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
    return photoExtension;
  }

}
