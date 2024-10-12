package agro.filelinkhub.domain.upload;

import agro.filelinkhub.configs.annotations.DomainService;
import agro.filelinkhub.domain.ComponentsMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@DomainService
@RequiredArgsConstructor
public class FileManagerImpl implements FileManager {

  private final FileNameGenerator generators;
  private final JsonWriter jsonWriter;
  private final ComponentsMap<Class<? extends File>, PresignedUrlGenerator<? extends File>> urlsGenerators;

  @Override
  @SneakyThrows
  public List<Link> upload(File file, int expiration) {
    generators.generate(file);
    var links = urlsGenerators.get(file.getClass()).generatePresignedUrl(file.impl(), expiration);
    jsonWriter.write(file);
    return links;
  }

}
