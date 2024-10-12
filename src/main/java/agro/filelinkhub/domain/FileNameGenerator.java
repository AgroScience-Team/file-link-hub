package agro.filelinkhub.domain;

@FunctionalInterface
public interface FileNameGenerator {

  void generate(File file);

}
