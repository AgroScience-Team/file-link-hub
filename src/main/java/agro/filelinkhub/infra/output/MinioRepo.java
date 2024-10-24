package agro.filelinkhub.infra.output;

import static io.minio.http.Method.GET;
import static io.minio.http.Method.PUT;

import agro.filelinkhub.domain.S3Repo;
import agro.filelinkhub.domain.exceptions.LinksGenerationException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MinioRepo implements S3Repo {

  private final MinioClient s3Client;

  @Override
  public String uploadUrl(String name, String bucket, int expiration) {
    try {
      return s3Client.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                      .bucket(bucket)
                      .object(name)
                      .method(PUT)
                      .expiry(expiration)
                      .build());
    } catch (Exception e) {
      throw new LinksGenerationException(e);
    }
  }

  @Override
  public String loadUrl(String name, String bucket, int expiration) {
    try {
      return s3Client.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                      .bucket(bucket)
                      .object(name)
                      .method(GET)
                      .expiry(expiration)
                      .build());
    } catch (Exception e) {
      throw new LinksGenerationException(e);
    }
  }

}