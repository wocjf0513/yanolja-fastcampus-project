package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryTestDataFileProvider;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItineraryCSVDaoTest {

  private final ItineraryCSVDao dao = new ItineraryCSVDao(new ItineraryTestDataFileProvider());
  private static File itineraryTestCSVDataFile;

  @BeforeAll
  static void beforeAll() {
    DataFileProvider dataFileProvider = new ItineraryTestDataFileProvider();
    itineraryTestCSVDataFile = dataFileProvider.getDataFile(1, FileFormat.CSV);
    itineraryTestCSVDataFile.deleteOnExit();
  }

  @DisplayName("여행에 대응하는 여정 파일을 만들 수 있어야 함")
  @Order(1)
  @Test
  void shouldCreateItineraryToFile() {
    dao.createItineraryToFile(itineraryTestCSVDataFile);
    assertTrue(itineraryTestCSVDataFile.exists());
  }


  @DisplayName("하나의 여정을 추가할 수 있고, 추가된 여정을 확인할 수 있어야 함")
  @Order(2)
  @Test
  void shouldAddItineraryToFile() {
    ItineraryCSV itineraryCSV = ItineraryCSV.builder()
        .departurePlace("City X")
        .destination("City Y")
        .departureTime("2023-08-15T08:00:00")
        .arrivalTime("2023-08-15T10:00:00")
        .checkIn("2023-08-15T12:00:00")
        .checkOut("2023-08-30T10:00:00")
        .build();
    dao.addItineraryToFile(itineraryTestCSVDataFile, itineraryCSV);

    List<ItineraryCSV> itineraries = dao.getItineraryListFromFile(itineraryTestCSVDataFile);

    assertEquals(itineraries.get(itineraries.size() - 1), itineraryCSV);
  }

  @DisplayName("여정 리스트를 파일에서 받아와야 함")
  @Order(3)
  @Test
  void shouldGetItineraryListFromFile() {
    String expected = "[ItineraryCSV(itineraryId=1, departurePlace=City X, destination=City Y, departureTime=2023-08-15T08:00:00, arrivalTime=2023-08-15T10:00:00, checkIn=2023-08-15T12:00:00, checkOut=2023-08-30T10:00:00)]";

    List<ItineraryCSV> itineraries = dao.getItineraryListFromFile(itineraryTestCSVDataFile);

    assertEquals(expected, Arrays.toString(itineraries.toArray()));
  }

  @DisplayName("하나의 여정을 파일에서 받아와야 함")
  @Order(4)
  @Test
  void shouldGetItineraryFromFile() {
    long itineraryId = 1;
    String expected = "ItineraryCSV(itineraryId=1, departurePlace=City X, destination=City Y, departureTime=2023-08-15T08:00:00, arrivalTime=2023-08-15T10:00:00, checkIn=2023-08-15T12:00:00, checkOut=2023-08-30T10:00:00)";

    ItineraryCSV itineraryCSV = dao.getItineraryFromFile(itineraryTestCSVDataFile, itineraryId);

    assertEquals(expected, itineraryCSV.toString());
  }
}
