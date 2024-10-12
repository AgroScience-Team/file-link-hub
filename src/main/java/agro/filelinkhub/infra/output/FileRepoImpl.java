package agro.filelinkhub.infra.output;

import agro.filelinkhub.domain.upload.File;
import agro.filelinkhub.domain.upload.FileRepo;
import asavershin.generated.package_.file_link_hub.tables.records.FilesRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepoImpl implements FileRepo {

  private final DSLContext dsl;

  @Override
  public void save(File file) {
    dsl.executeInsert(new FilesRecord(file.name(), file.bucket()));
  }

}
