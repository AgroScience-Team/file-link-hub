package agro.filelinkhub.domain.load;

import agro.filelinkhub.configs.annotations.Query;
import agro.filelinkhub.domain.S3Repo;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class LoadFileManager {

  private final S3Repo s3Repo;

  public List<String> load(List<String> fileNames, String bucket, int expiration) {
    return fileNames.stream().map(fn -> s3Repo.loadUrl(fn, bucket, expiration)).toList();
  }

}