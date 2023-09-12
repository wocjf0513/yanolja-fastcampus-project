package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryTestDataFileProvider;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItineraryJSONDaoTest {

    private final ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();
    private final static DataFileProvider dataFileProvider = new ItineraryTestDataFileProvider();

    @BeforeAll
    static void beforeAll() {
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(1, FileFormat.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
    }

    @DisplayName("여행에 대응하는 여정 파일을 만들 수 있어야 함")
    @Order(1)
    @Test
    void shouldCreateItineraryToFile() {
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(1, FileFormat.JSON);
        itineraryJSONDao.createItineraryToFile(itineraryTestJSONDataFile);
        assertTrue(itineraryTestJSONDataFile.exists());
    }

    @DisplayName("여정 리스트를 파일에서 받아와야 함")
    @Test
    void shouldGetItineraryListFromFile() {
        long tripId = 1;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileFormat.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        var list = itineraryJSONDao.getItineraryListFromFile(itineraryTestJSONDataFile);
    }

    @DisplayName("존재하지 않는 여행 파일에 여정을 추가한 경우")
    @Test
    void shouldThrowNullPointerExceptionWhenAddingItineraryToNonexistentTrip() {
        long tripId = Integer.MAX_VALUE;
        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileFormat.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        assertThrows(FileIOException.class, () -> {
            itineraryJSONDao.addItineraryToFile(itineraryTestJSONDataFile, itinerary);
        });

    }

    @DisplayName("원하는 인덱스의 여정을 가져와야 함")
    @Test
    void shouldGetItineraryAtIndex() {
        long tripId = 1;
        long itineraryId = 1;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileFormat.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        itineraryJSONDao.getItineraryFromFile(itineraryTestJSONDataFile, itineraryId);
    }

    @DisplayName("마지막으로 추가된 여정 파일의 인덱스가 일치하는지 확인해야 함")
    @Test
    void shouldCheckIfLastAddedItineraryIndexMatches() {

        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();

        long tripId = 1;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileFormat.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        itineraryJSONDao.addItineraryToFile(itineraryTestJSONDataFile, itinerary);
        var list = itineraryJSONDao.getItineraryListFromFile(itineraryTestJSONDataFile);

       
        assertEquals(list.get(list.size()-1).getItineraryId(), itinerary.getItineraryId());

    }
}
