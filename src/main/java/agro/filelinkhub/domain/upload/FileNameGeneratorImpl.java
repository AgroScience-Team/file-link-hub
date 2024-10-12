package agro.filelinkhub.domain.upload;

import agro.filelinkhub.configs.annotations.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Command
@RequiredArgsConstructor
@Slf4j
public class FileNameGeneratorImpl implements FileNameGenerator {

  private final FileRepo repo;

  @Override
  public void generate(File file) {
    while (true) {
      try {
        repo.save(file);
        return;
      } catch (Exception e) {
        log.error("Collision with file [{}]", file, e);
        file.generateName();
      }
    }
  }

}
