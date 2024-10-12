package agro.filelinkhub;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import agro.filelinkhub.domain.upload.Link;
import agro.filelinkhub.domain.upload.tiff.MultiLayerTiff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ApiTests extends HttpSteps {

  @Autowired
  ObjectMapperHelper mapper;

  @Test
  @SuppressWarnings("ConstantConditions")
  void testUploadEndpoint() {
    // When
    var response = sengPostRequest(
            "/api/v1/filelinkhub/upload?expiration=60",
            """
                    {
                      "fieldId": "BaC131D5-113F-da4b-B0F5-391BfA183EcF",
                      "photoDate": "2024-10-10",
                      "photoExtension": "tif",
                      "layers": ["red", "green", "blue", "nir"]
                    }
                    """
    );

    // Then
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response);
    assertNotNull(response.getBody());
    if (response.getBody() != null) {
      var links = mapper.parseList(response.getBody(), Link.class);
      assertEquals(1, links.size());

      var link = links.get(0);
      assertNotNull(link.url());
      assertNotNull(link.extension());
      assertEquals("tif", link.extension());

      String fileName = fileName(link.url());
      var jsonTiff = getFile(fileName + ".json", "agro-photos", MultiLayerTiff.class);
      assertNotNull(jsonTiff);
      assertEquals(jsonTiff.getType(), "MultiLayerTiff");
      assertEquals(jsonTiff.getLayers().size(), 4);
    }
  }

}
