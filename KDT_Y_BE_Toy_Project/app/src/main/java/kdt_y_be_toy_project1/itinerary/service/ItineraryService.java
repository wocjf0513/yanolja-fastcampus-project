package kdt_y_be_toy_project1.itinerary.service;

import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.itinerary.dao.ItineraryCSVDao;
import kdt_y_be_toy_project1.itinerary.dao.ItineraryJSONDao;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import kdt_y_be_toy_project1.itinerary.dto.ItineraryResponse;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.service.TripPeriodMismatchException;

import java.util.List;

public class ItineraryService {

  private final ItineraryJSONDao jsonDao;
  private final ItineraryCSVDao csvDao;

  public ItineraryService() {
    this.jsonDao = new ItineraryJSONDao();
    this.csvDao = new ItineraryCSVDao();
  }

  public ItineraryService(DataFileProvider dataFileProvider) {
    this.jsonDao = new ItineraryJSONDao(dataFileProvider);
    this.csvDao = new ItineraryCSVDao(dataFileProvider);
  }

  public List<ItineraryResponse> getAllItineraryList(long tripId, FileFormat type) {
    // fileApi를 통해 Itinerary 객체 리스트를 받아온다
    if (type.equals(FileFormat.JSON)) {
      return jsonDao.getItineraryListByTripId(tripId)
          .stream().map(ItineraryResponse::fromJSONEntity)
          .toList();
    } else {
      return csvDao.getItineraryListByTripId(tripId)
          .stream().map(ItineraryResponse::fromCSVEntity)
          .toList();
    }
  }

  public ItineraryResponse getItinerary(long tripId, long itineraryId, FileFormat type) {
    // fileApi를 통해 Itinerary 객체를 받아온다

    if (type.equals(FileFormat.JSON)) {
      return ItineraryResponse
          .fromJSONEntity(jsonDao.getItineraryById(tripId, itineraryId));
    } else {
      return ItineraryResponse
          .fromCSVEntity(csvDao.getItineraryById(tripId, itineraryId));
    }
  }

  public void createItinerary(long tripId) {
    // 여행 인덱스에 대응하는 여정 파일을 만든다
    csvDao.createItineraryByTripId(tripId);
    jsonDao.createItineraryByTripId(tripId);
  }

  public void addItinerary(long tripId, AddItineraryRequest request) {
    // fileApi를 통해 Itinerary 객체를 넣는다
    validAddItineraryRequest(request);

    csvDao.addItineraryByTripId(tripId, ItineraryCSV.from(request));
    jsonDao.addItineraryByTripId(tripId, ItineraryJSON.from(request));
  }

  private void validAddItineraryRequest(AddItineraryRequest request){

    if(request.getArrivalTime().isBefore(request.getDepartureTime())){
      throw new TripPeriodMismatchException("도착 시간이 출발 시간보다 빠를 수 없습니다.");
    }
    if(request.getCheckOut().isBefore(request.getCheckIn())){
      throw new TripPeriodMismatchException("체크아웃 시간이 체크인 시간보다 빠를 수 없습니다.");
    }
  }
}
