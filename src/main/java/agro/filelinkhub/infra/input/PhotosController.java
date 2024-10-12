package agro.filelinkhub.infra.input;

import agro.filelinkhub.domain.upload.FileManager;
import agro.filelinkhub.domain.upload.Link;
import agro.filelinkhub.infra.input.dto.MultiLayerTiffUploadRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/filelinkhub")
@RequiredArgsConstructor
public class PhotosController {

  private final FileManager manager;

  @PostMapping("/upload")
  @Operation(description = "Запрос на получение ссылки для загрузки")
  public List<Link> register(
          final int expiration,
          final @RequestBody @Valid MultiLayerTiffUploadRequest request
  ) {
    return manager.upload(request.map(), expiration);
  }

}
