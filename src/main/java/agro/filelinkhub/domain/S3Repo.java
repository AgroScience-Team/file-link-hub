package agro.filelinkhub.domain;

public interface S3Repo {

  String uploadUrl(String name, String bucket, int expiration);

  String loadUrl(String name, String bucket, int expiration);

}