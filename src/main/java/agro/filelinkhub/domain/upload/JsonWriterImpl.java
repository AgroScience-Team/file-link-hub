package agro.filelinkhub.domain.upload;

import agro.filelinkhub.configs.annotations.Command;
import agro.filelinkhub.domain.S3Repo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Command
@RequiredArgsConstructor
@Slf4j
public class JsonWriterImpl implements JsonWriter {

  private final S3Repo s3Repo;
  private final ObjectMapper mapper;

  @Override
  public void write(File file) {

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

      mapper.writeValue(byteArrayOutputStream, file);
      byteArrayOutputStream.flush();

      try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
        s3Repo.upload(inputStream, file.bucket(), file.name() + ".json", byteArrayOutputStream.size());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
