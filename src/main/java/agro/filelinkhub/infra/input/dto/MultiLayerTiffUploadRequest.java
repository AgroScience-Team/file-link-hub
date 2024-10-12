package agro.filelinkhub.infra.input.dto;

import static agro.filelinkhub.infra.input.dto.Constants.NOT_NULL_MESSAGE;

import agro.filelinkhub.domain.upload.File;
import agro.filelinkhub.domain.upload.tiff.MultiLayerTiff;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MultiLayerTiffUploadRequest implements FileDto {

  @NotNull(message = NOT_NULL_MESSAGE)
  @Pattern(regexp = "^[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}$", message = "Ожидается UUID")
  private String fieldId;
  @NotNull(message = NOT_NULL_MESSAGE)
  @PastOrPresent(message = "Должно быть в прошлом или настоящем")
  private LocalDate photoDate;
  @NotNull(message = NOT_NULL_MESSAGE)
  @Pattern(regexp = "^(tif|tiff)$", message = "Должно быть 'tif' или 'tiff'")
  private String photoExtension;
  @NotNull(message = NOT_NULL_MESSAGE)
  @NotEmpty(message = NOT_NULL_MESSAGE)
  private List<String> layers;

  @Override
  public File map() {
    return new MultiLayerTiff(fieldId, photoDate, photoExtension, layers);
  }

}
