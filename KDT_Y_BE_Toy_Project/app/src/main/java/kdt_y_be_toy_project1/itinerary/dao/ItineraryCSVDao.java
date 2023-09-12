package kdt_y_be_toy_project1.itinerary.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import kdt_y_be_toy_project1.itinerary.exception.format.CsvParseException;
import kdt_y_be_toy_project1.itinerary.exception.service.ItineraryNotFoundException;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static kdt_y_be_toy_project1.common.util.FileFormat.CSV;

public class ItineraryCSVDao implements ItineraryDao<ItineraryCSV> {

  private final DataFileProvider dataFileProvider;

  public ItineraryCSVDao() {
    this.dataFileProvider = new ItineraryDataFile();
  }

  public ItineraryCSVDao(DataFileProvider dataFileProvider) {
    this.dataFileProvider = dataFileProvider;
  }

  @Override
  public List<ItineraryCSV> getItineraryListByTripId(long tripId) {
    return getItineraryListFromFile(dataFileProvider.getDataFile(tripId, CSV));
  }

  @Override
  public ItineraryCSV getItineraryById(long tripId, long itineraryId) {
    return getItineraryFromFile(dataFileProvider.getDataFile(tripId, CSV), itineraryId);
  }

  @Override
  public void createItineraryByTripId(long tripId) {
    createItineraryToFile(dataFileProvider.getDataFile(tripId, CSV));
  }

  @Override
  public void addItineraryByTripId(long tripId, ItineraryCSV itineraryCSV) {
    addItineraryToFile(dataFileProvider.getDataFile(tripId, CSV), itineraryCSV);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  public List<ItineraryCSV> getItineraryListFromFile(File itineraryFile) {
    Reader bufferedReader = createFileReader(itineraryFile);
    return parseCsvToList(bufferedReader, itineraryFile);
  }

  public ItineraryCSV getItineraryFromFile(File itineraryFile, long itineraryId) {
    return getItineraryListFromFile(itineraryFile).stream()
        .filter(it -> it.getItineraryId() == itineraryId)
        .findFirst().orElseThrow(() -> new ItineraryNotFoundException("찾으시려는 여정이 존재하지 않습니다."));
  }

  public void createItineraryToFile(File file) {
    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new FileIOException("파일이 이미 존재하거나 만들 수 없습니다.");
    }
  }

  public void addItineraryToFile(File itineraryFile, ItineraryCSV itinerary) {
    List<ItineraryCSV> itineraries = getItineraryListFromFile(itineraryFile);
    if (itineraries.isEmpty()) {
      itinerary.setItineraryId(1);
      itineraries = Collections.singletonList(itinerary);
    } else {
      itinerary.setItineraryId(itineraries.size() + 1);
      itineraries.add(itinerary);
    }

    writeCsvToFile(itineraryFile, itineraries);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  public Reader createFileReader(File file) {
    Reader bufferedReader;
    try {
      bufferedReader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new FileIOException("파일을 읽을 수 없습니다.");
    }
    return bufferedReader;
  }

  public List<ItineraryCSV> parseCsvToList(Reader bufferedReader, File itineraryFile) {
    List<ItineraryCSV> itineraries = Collections.emptyList();
    try {
      if (!itineraryFile.createNewFile()) {
        List<ItineraryCSV> temp = new CsvToBeanBuilder<ItineraryCSV>(bufferedReader)
            .withType(ItineraryCSV.class)
            .build()
            .parse();
        if (temp != null) itineraries = temp;
        bufferedReader.close();
      }
    } catch (IllegalStateException | IOException e) {
      throw new CsvParseException("읽으려는 파일이 Csv 파일 형식에 맞지 않습니다.");
    }

    return itineraries;
  }

  public void writeCsvToFile(File file, List<ItineraryCSV> itineraries) {
    try (FileWriter writer = new FileWriter(file)) {
      StatefulBeanToCsv<ItineraryCSV> beanToCsv = new StatefulBeanToCsvBuilder<ItineraryCSV>(writer).build();
      beanToCsv.write(itineraries);
    } catch (IOException | CsvException e) {
      throw new FileIOException("File에 내용을 쓸 수 없습니다.");
    }
  }
}
