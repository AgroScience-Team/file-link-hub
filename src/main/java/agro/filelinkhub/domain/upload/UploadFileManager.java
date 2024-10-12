package agro.filelinkhub.domain.upload;

import java.util.List;

@FunctionalInterface
public interface UploadFileManager {

  List<UploadLink> upload(File file, int expiration);

}