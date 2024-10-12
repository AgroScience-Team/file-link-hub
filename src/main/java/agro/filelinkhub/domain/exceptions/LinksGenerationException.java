package agro.filelinkhub.domain.exceptions;

public class LinksGenerationException extends RuntimeException {

  public LinksGenerationException(Throwable cause) {
    super("Возникла ошибка при формировании ссылок", cause);
  }

}
