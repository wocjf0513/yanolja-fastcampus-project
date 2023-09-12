package kdt_y_be_toy_project1.common.data;

import kdt_y_be_toy_project1.common.util.FileFormat;
import org.junit.jupiter.api.Test;

import java.io.File;

class ItineraryDataPathTest {

  @Test
  void shouldGetItineraryFilePathString() {
    File file = new ItineraryDataFile().getDataFile(1, FileFormat.CSV);
    System.out.println(file);
  }
}
