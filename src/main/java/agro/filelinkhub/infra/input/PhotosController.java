package agro.filelinkhub.infra.input;

import agro.filelinkhub.domain.load.LoadFileManager;
import agro.filelinkhub.domain.upload.UploadFileManager;
import agro.filelinkhub.domain.upload.UploadLink;
import agro.filelinkhub.infra.input.dto.LoadRequest;
import agro.filelinkhub.infra.input.dto.MultiLayerTiffUploadRequest;
import agro.filelinkhub.infra.mappers.FileMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/file-link-hub")
@RequiredArgsConstructor
public class PhotosController {

  private final UploadFileManager uploadFileManager;
  private final LoadFileManager loadFileManager;
  private final FileMapper mapper;

  @PostMapping("/upload/multi-layer-tiff")
  @Operation(description = "Запрос на получение ссылки для загрузки")
  public List<UploadLink> register(
          final int expiration,
          final @RequestBody @Valid MultiLayerTiffUploadRequest request
  ) {
    return uploadFileManager.upload(mapper.map(request), expiration);
  }

  @PostMapping("/load")
  @Operation(description = "Запрос на получение ссылки для загрузки")
  public List<String> register(
          final int expiration,
          final @RequestBody @Valid LoadRequest request
  ) {
    return loadFileManager.load(request.getFileNames(), request.getBucket(), expiration);
  }

}
