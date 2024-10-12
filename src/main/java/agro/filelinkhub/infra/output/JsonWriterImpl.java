package agro.filelinkhub.infra.output;

import agro.filelinkhub.configs.annotations.Command;
import agro.filelinkhub.domain.File;
import agro.filelinkhub.domain.JsonWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Command
@RequiredArgsConstructor
@Slf4j
public class JsonWriterImpl implements JsonWriter {

  private final MinioClient s3Client;
  private final ObjectMapper mapper;

  @Override
  public void write(File file) {

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

      mapper.writeValue(byteArrayOutputStream, file);
      byteArrayOutputStream.flush();

      try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
        uploadToS3(inputStream, file.bucket(), file.name() + ".json", byteArrayOutputStream.size());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SneakyThrows
  private void uploadToS3(ByteArrayInputStream inputStream, String bucketName, String objectName, int size) {
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

}
