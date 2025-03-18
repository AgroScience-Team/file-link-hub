package agro.filelinkhub.infra.input.dto;

import static agro.filelinkhub.infra.input.dto.Constants.NOT_NULL_MESSAGE;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class MultiLayerTiffUploadRequest {

  @NotNull(message = NOT_NULL_MESSAGE)
  @Pattern(regexp = "^[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}$", message = "Ожидается UUID")
  private String contourId;

  @NotNull(message = NOT_NULL_MESSAGE)
  @PastOrPresent(message = "Должно быть в прошлом или настоящем")
  private Instant date;

  @NotNull(message = NOT_NULL_MESSAGE)
  @Pattern(regexp = "^(tif|tiff)$", message = "Должно быть 'tif' или 'tiff'")
  private String extension;

  @NotNull(message = NOT_NULL_MESSAGE)
  @NotEmpty(message = NOT_NULL_MESSAGE)
  private List<LayerDTO> layers;

}