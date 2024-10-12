package agro.filelinkhub.domain.tiff;

import static io.minio.http.Method.PUT;

import agro.filelinkhub.configs.annotations.Query;
import agro.filelinkhub.domain.Link;
import agro.filelinkhub.domain.PresignedUrlGenerator;
import agro.filelinkhub.domain.exceptions.LinksGenerationException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Query
@RequiredArgsConstructor
public class MultiLayerTiffPresignedUrlGenerator implements PresignedUrlGenerator<MultiLayerTiff> {

  private final MinioClient s3Client;

  @Override
  public Class<MultiLayerTiff> key() {
    return MultiLayerTiff.class;
  }

  @Override
  public List<Link> generatePresignedUrl(MultiLayerTiff file, int expiration) {
    try {
      return List.of(new Link(file.extension(), s3Client.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                      .bucket(file.bucket())
                      .object(file.name() + "." + file.extension())
                      .method(PUT)
                      .expiry(expiration)
                      .build()))
      );
    } catch (Exception e) {
      throw new LinksGenerationException(e);
    }
  }

}
