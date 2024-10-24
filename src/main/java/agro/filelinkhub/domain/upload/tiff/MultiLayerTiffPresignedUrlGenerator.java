package agro.filelinkhub.domain.upload.tiff;

import agro.filelinkhub.configs.annotations.Query;
import agro.filelinkhub.domain.S3Repo;
import agro.filelinkhub.domain.exceptions.LinksGenerationException;
import agro.filelinkhub.domain.upload.PresignedUrlGenerator;
import agro.filelinkhub.domain.upload.UploadLink;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Query
@RequiredArgsConstructor
public class MultiLayerTiffPresignedUrlGenerator implements PresignedUrlGenerator<MultiLayerTiff> {

  private final S3Repo s3Repo;

  @Value("${images.prefix}")
  private String prefix;

  @Override
  public Class<MultiLayerTiff> key() {
    return MultiLayerTiff.class;
  }

  @Override
  public List<UploadLink> generatePresignedUrl(MultiLayerTiff file, int expiration) {
    try {
      String fullName = prefix + file.name() + "." + file.extension();
      return List.of(new UploadLink(file.extension(), s3Repo.uploadUrl(fullName, file.bucket(), expiration)));
    } catch (Exception e) {
      throw new LinksGenerationException(e);
    }
  }

}
