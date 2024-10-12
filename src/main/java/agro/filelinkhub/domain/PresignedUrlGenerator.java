package agro.filelinkhub.domain;

import java.util.List;

public interface PresignedUrlGenerator<T extends File> {

  Class<T> key();

  List<Link> generatePresignedUrl(T file, int expiration);

}
