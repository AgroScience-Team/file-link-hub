package agro.filelinkhub;


import static org.springframework.http.HttpMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class HttpSteps extends S3Steps {

  @Autowired
  protected TestRestTemplate restTemplate;

  public ResponseEntity<String> sendHttpRequest(
          final String url,
          final HttpMethod method,
          HttpHeaders headers,
          final String requestBody
  ) {
    if (headers == null) {
      headers = new HttpHeaders();
    }
    HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
    return restTemplate.exchange(url, method, entity, String.class);
  }

  public ResponseEntity<String> sengPostRequest(
          final String url,
          final String requestBody

  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    return sendHttpRequest(url, POST, headers, requestBody);
  }

}
