package agro.filelinkhub.infra.input.dto;

import static agro.filelinkhub.infra.input.dto.Constants.NOT_NULL_MESSAGE;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadRequest {

  @NotNull(message = NOT_NULL_MESSAGE)
  private String bucket;
  @NotNull(message = NOT_NULL_MESSAGE)
  @NotEmpty(message = NOT_NULL_MESSAGE)
  private List<String> fileNames;

}