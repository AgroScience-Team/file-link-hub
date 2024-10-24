package agro.filelinkhub.infra.output;

import static io.minio.http.Method.GET;
import static io.minio.http.Method.PUT;

import agro.filelinkhub.domain.S3Repo;
import agro.filelinkhub.domain.exceptions.LinksGenerationException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MinioRepo implements S3Repo {

  private final MinioClient s3Client;

  private final MongoTemplate mongoTemplate;

  @Override
  @SneakyThrows
  public void upload(ByteArrayInputStream inputStream, String bucketName, String objectName, int size) {
    s3Client.putObject(
            PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType("application/json")
                    .build()
    );
    log.info("File uploaded to s3: {} / {}}", bucketName, objectName);
  }

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

  @Override
  public void save(Object object) {
    mongoTemplate.save(object);
  }

}
