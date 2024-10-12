package agro.filelinkhub.domain.upload;

@FunctionalInterface
public interface FileNameGenerator {

  void generate(File file);

}
