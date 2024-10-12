package agro.filelinkhub.domain;

import java.util.List;

@FunctionalInterface
public interface FileManager {

  List<Link> upload(File file, int expiration);

}