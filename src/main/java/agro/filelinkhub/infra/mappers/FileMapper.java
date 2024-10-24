package agro.filelinkhub.infra.mappers;

import agro.filelinkhub.domain.upload.tiff.MultiLayerTiff;
import agro.filelinkhub.infra.input.dto.MultiLayerTiffUploadRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

  MultiLayerTiff map(MultiLayerTiffUploadRequest request);

}