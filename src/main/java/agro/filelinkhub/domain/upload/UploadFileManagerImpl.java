package agro.filelinkhub.domain.upload;

import agro.filelinkhub.configs.annotations.DomainService;
import agro.filelinkhub.domain.ComponentsMap;
import agro.filelinkhub.domain.DocRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@DomainService
@RequiredArgsConstructor
public class UploadFileManagerImpl implements UploadFileManager {

  private final FileNameGenerator generators;
  private final DocRepo docRepo;
  private final ComponentsMap<Class<? extends File>, PresignedUrlGenerator<? extends File>> urlsGenerators;

  @Override
  @SneakyThrows
  public List<UploadLink> upload(File file, int expiration) {
    generators.generate(file);
    var links = urlsGenerators.get(file.getClass()).generatePresignedUrl(file.impl(), expiration);
    docRepo.save(file);
    return links;
  }

}
