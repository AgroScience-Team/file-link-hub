package agro.filelinkhub.domain;

import java.io.ByteArrayInputStream;

public interface S3Repo {

  void upload(ByteArrayInputStream inputStream, String bucketName, String objectName, int size);

  String uploadUrl(String name, String bucket, int expiration);

  String loadUrl(String name, String bucket, int expiration);

  void save(Object object);

}