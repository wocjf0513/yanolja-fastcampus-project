package kdt_y_be_toy_project1.itinerary.dao;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import kdt_y_be_toy_project1.itinerary.exception.format.JsonParseException;
import kdt_y_be_toy_project1.itinerary.exception.service.ItineraryNotFoundException;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static kdt_y_be_toy_project1.common.util.FileFormat.JSON;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {

  private final DataFileProvider dataFileProvider;

  public ItineraryJSONDao() {
    this.dataFileProvider = new ItineraryDataFile();
  }

  public ItineraryJSONDao(DataFileProvider dataFileProvider) {
    this.dataFileProvider = dataFileProvider;
  }

  @Override
  public List<ItineraryJSON> getItineraryListByTripId(long tripId) {
    return getItineraryListFromFile(dataFileProvider.getDataFile(tripId, JSON));
  }

  @Override
  public ItineraryJSON getItineraryById(long tripId, long itineraryId) {
    return getItineraryFromFile(dataFileProvider.getDataFile(tripId, JSON), itineraryId);
  }

  @Override
  public void createItineraryByTripId(long tripId) {
    createItineraryToFile(dataFileProvider.getDataFile(tripId, JSON));
  }

  @Override
  public void addItineraryByTripId(long tripId, ItineraryJSON itineraryJSON) {
    addItineraryToFile(dataFileProvider.getDataFile(tripId, JSON), itineraryJSON);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  public List<ItineraryJSON> getItineraryListFromFile(File itineraryFile) {
    JsonReader jsonReader = createFileReader(itineraryFile);
    return parseJsonToList(jsonReader, itineraryFile);
  }

  public ItineraryJSON getItineraryFromFile(File itineraryFile, long itineraryId) {
    return getItineraryListFromFile(itineraryFile).stream()
        .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
        .findFirst().orElseThrow(() -> new ItineraryNotFoundException("찾으시려는 여정이 존재하지 않습니다."));
  }

  public void createItineraryToFile(File file) {
    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new FileIOException("파일이 이미 존재하거나 만들 수 없습니다.");
    }
  }

  public void addItineraryToFile(File itineraryFile, ItineraryJSON itinerary) {
    List<ItineraryJSON> itineraries = getItineraryListFromFile(itineraryFile);
    if (itineraries.isEmpty()) {
      itinerary.setItineraryId(1);
      itineraries = Collections.singletonList(itinerary);
    } else {
      itinerary.setItineraryId(itineraries.size() + 1);
      itineraries.add(itinerary);
    }

    String jsonArray = convertItineraryListToJson(itineraries);
    writeJsonToFile(itineraryFile, jsonArray);
  }

//////////////////////////////////////////////////////////////////////////////////////////////////

  public JsonReader createFileReader(File file) {
    JsonReader bufferedReader;
    try {
      bufferedReader = new JsonReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new FileIOException("파일을 읽을 수 없습니다.");
    }
    return bufferedReader;
  }


  public List<ItineraryJSON> parseJsonToList(JsonReader jsonReader, File itineraryFile) {
    List<ItineraryJSON> itineraries = Collections.emptyList();
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    try {
      if (!itineraryFile.createNewFile()) {
        List<ItineraryJSON> temp = gson.fromJson(jsonReader,
                      new TypeToken<List<ItineraryJSON>>() {}.getType());
        if (temp != null) itineraries = temp;
        jsonReader.close();
      }
    } catch (JsonSyntaxException e) {
      throw new JsonParseException("읽으려는 파일이 Json 파일 형식에 맞지 않습니다.");
    } catch (IOException e) {
      throw new FileIOException("파일을 읽을 수 없습니다.");
    }
    return itineraries;
  }

  public String convertItineraryListToJson(List<ItineraryJSON> itineraries) {
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    String response;
    try {
      response = gson.toJson(itineraries);
    } catch (JsonParseException e) {
      throw new JsonParseException("Json 파일 형식에 맞지 않습니다.");
    }
    return response;
  }

  public void writeJsonToFile(File file, String jsonArray) {
    FileWriter writer;
    try {
      writer = new FileWriter(file);
      writer.write(jsonArray);
      writer.close();
    } catch (IOException e) {
      throw new FileIOException("File을 쓸 수 없습니다.");
    }
  }
}
