package agro.filelinkhub.domain;

import agro.filelinkhub.configs.annotations.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Query
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
