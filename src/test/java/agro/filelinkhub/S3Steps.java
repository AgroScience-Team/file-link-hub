package agro.filelinkhub;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.messages.Item;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class S3Steps extends AbstractTest {

  private static final String BUCKET_NAME = "agro-photos";
  @Autowired
  private MinioClient minioClient;
  @Autowired
  private ObjectMapper mapper;

  @SneakyThrows
  public static String fileName(String s3Url) {
    URI uri = new URI(s3Url);
    String path = uri.getPath();

    if (path.startsWith("/")) {
      path = path.substring(1);
    }

    String[] parts = path.split("/");

    if (parts.length >= 2) {
      var fullName = parts[parts.length - 1];
      String[] fileNameParts = fullName.split("\\.");
      return fileNameParts[0];
    }

    throw new IllegalArgumentException("Invalid S3 URL: " + s3Url);
  }

  @BeforeEach
  @SneakyThrows
  public void setUp() {
    if (!bucketExists(BUCKET_NAME)) {
      createBucket(BUCKET_NAME);
    }
  }

  @SneakyThrows
  private boolean bucketExists(String bucketName) {
    return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
  }

  @SneakyThrows
  private void createBucket(String bucketName) {
    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
  }

  @AfterEach
  @SneakyThrows
  public void tearDown() {
    if (minioClient != null && bucketExists(BUCKET_NAME)) {
      clearBucket(BUCKET_NAME);
    }
  }

  @SneakyThrows
  private void clearBucket(String bucketName) {
    var objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    for (var result : objects) {
      Item item = result.get();
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
    }
  }

  @SneakyThrows
  protected <T> T getFile(String name, String bucket, Class<T> clazz) {
    try (InputStream inputStream = minioClient.getObject(
            GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build())) {

      return mapper.readValue(inputStream, clazz);

    }
  }

  @SneakyThrows
  protected void fileExistsInBucket(String name, String bucket, byte[] content) {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
    minioClient.putObject(
            PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .stream(inputStream, content.length, -1)
                    .contentType("application/octet-stream")
                    .build()
    );
  }

}
