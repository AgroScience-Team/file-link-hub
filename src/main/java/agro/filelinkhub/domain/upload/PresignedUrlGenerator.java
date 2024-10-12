package agro.filelinkhub.domain.upload;

import java.util.List;

public interface PresignedUrlGenerator<T extends File> {

  Class<T> key();

  List<UploadLink> generatePresignedUrl(T file, int expiration);

}
