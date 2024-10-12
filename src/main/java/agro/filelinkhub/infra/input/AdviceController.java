package agro.filelinkhub.infra.input;

import agro.filelinkhub.domain.exceptions.LinksGenerationException;
import agro.filelinkhub.infra.input.dto.ExceptionBody;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AdviceController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleValidationException(final MethodArgumentNotValidException e) {
    var exceptionBody = new ExceptionBody("Validation failed");
    Map<String, String> body = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage
    ));
    exceptionBody.setErrors(body);
    return exceptionBody;
  }

  @ExceptionHandler(LinksGenerationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleLinksGenerationExceptionException(final LinksGenerationException e) {
    log.error("{}", e.getMessage(), e);
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleException(final RuntimeException e) {
    log.error("Непредвиденная ошибка", e);
    return new ExceptionBody("Непредвиденная ошибка");
  }

}
