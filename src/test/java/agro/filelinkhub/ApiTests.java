package agro.filelinkhub;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import agro.filelinkhub.domain.upload.UploadLink;
import agro.filelinkhub.models.MultiLayerTiffTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

class ApiTests extends MongoSteps {

  @Autowired
  ObjectMapperHelper mapper;

  @Test
  @SuppressWarnings("ConstantConditions")
  void testUploadMultiLayersTiffEndpoint() {
    // When
    var response = sengPostRequest(
            "/api/v1/file-link-hub/upload/multi-layer-tiff?expiration=60",
            """
                    {
                      "contourId": "00000000-0000-0000-0000-000000000002",
                      "date": "2024-08-02T12:00:00Z",
                      "extension": "tif",
                      "layers": [
                        {
                          "index": 1,
                          "name": "red"
                        },
                        {
                          "index": 2,
                          "name": "green"
                        },
                        {
                          "index": 3,
                          "name": "blue"
                        },
                        {
                          "index": 5,
                          "name": "nir"
                        }
                      ]
                    }
                    """
    );

    // Then
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response);
    assertNotNull(response.getBody());
    if (response.getBody() != null) {
      var links = mapper.parseList(response.getBody(), UploadLink.class);
      assertEquals(1, links.size());

      var link = links.get(0);
      assertNotNull(link.url());
      assertNotNull(link.extension());
      assertEquals("tif", link.extension());

      String fileName = fileName(link.url());
      var jsonTiff = findById(fileName, MultiLayerTiffTest.class);
      assertNotNull(jsonTiff);
      assertEquals(jsonTiff.getType(), "MultiLayerTiff");
      assertEquals(jsonTiff.getContourId(), "00000000-0000-0000-0000-000000000002");
      assertEquals(jsonTiff.getDate().toString(), "2024-08-02T12:00:00Z");
      assertEquals(jsonTiff.getLayers().size(), 4);
    }
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void testLoadEndpoint() {
    // Given
    String fileName1 = "testFile1.txt";
    String fileName2 = "testFile2.txt";
    byte[] content1 = "Test content 1".getBytes();
    byte[] content2 = "Test content 2".getBytes();
    fileExistsInBucket(fileName1, "agro-photos", content1);
    fileExistsInBucket(fileName2, "agro-photos", content2);

    // When
    ResponseEntity<String> response = sengPostRequest(
            "/api/v1/file-link-hub/load?expiration=60",
            """
              {
                "bucket": "agro-photos",
                "fileNames": [
                  "testFile1.txt",
                  "testFile2.txt"
                ]
              }
            """);

    // Then
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response);
    assertNotNull(response.getBody());
    if (response.getBody() != null) {
      List<String> links = mapper.parseList(response.getBody(), String.class);
      assertEquals(2, links.size());

      for (String link : links) {
        assertNotNull(link);
        String fileName = fileName(link) + ".txt";
        assertTrue(fileName.equals(fileName1) || fileName.equals(fileName2));
      }
    }
  }

}
