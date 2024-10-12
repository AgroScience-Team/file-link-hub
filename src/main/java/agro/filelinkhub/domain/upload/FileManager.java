package agro.filelinkhub.domain.upload;

import java.util.List;

@FunctionalInterface
public interface FileManager {

  List<Link> upload(File file, int expiration);

}