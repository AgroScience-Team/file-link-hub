package agro.filelinkhub.infra.input.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {

  private String message;
  private Map<String, String> errors;

  public ExceptionBody(final String message) {
    this.message = message;
  }

}
