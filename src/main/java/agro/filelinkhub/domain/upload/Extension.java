package agro.filelinkhub.domain.upload;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Extension {
  TIFF("^(tif|tiff)$");

  private final String regex;

  public static Extension extensionByKey(String key) {
    return Arrays.stream(values())
            .filter(e -> key.matches(e.regex))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown extension: " + key));
  }

}
