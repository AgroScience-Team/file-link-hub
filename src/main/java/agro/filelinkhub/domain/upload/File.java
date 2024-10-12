package agro.filelinkhub.domain.upload;

public interface File {

  String extension();

  void generateName();

  String name();

  String bucket();

  @SuppressWarnings("unchecked")
  default <T> T impl() {
    return (T) this;
  }

}