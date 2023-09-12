package kdt_y_be_toy_project1.itinerary.dto;

import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class ItineraryResponse {
  private int itineraryId;
  private String departurePlace;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private LocalDateTime checkIn;
  private LocalDateTime checkOut;

  public static ItineraryResponse fromCSVEntity(ItineraryCSV itinerary) {
    return ItineraryResponse.builder()
        .itineraryId(itinerary.getItineraryId())
        .departurePlace(itinerary.getDeparturePlace())
        .destination(itinerary.getDestination())
        .departureTime(LocalDateTime.parse(itinerary.getDepartureTime()))
        .arrivalTime(LocalDateTime.parse(itinerary.getArrivalTime()))
        .checkIn(LocalDateTime.parse(itinerary.getCheckIn()))
        .checkOut(LocalDateTime.parse(itinerary.getCheckOut()))
        .build();
  }

  public static ItineraryResponse fromJSONEntity(ItineraryJSON itinerary) {
    return ItineraryResponse.builder()
        .itineraryId(itinerary.getItineraryId())
        .departurePlace(itinerary.getDeparturePlace())
        .destination(itinerary.getDestination())
        .departureTime(LocalDateTime.parse(itinerary.getDepartureTime()))
        .arrivalTime(LocalDateTime.parse(itinerary.getArrivalTime()))
        .checkIn(LocalDateTime.parse(itinerary.getCheckIn()))
        .checkOut(LocalDateTime.parse(itinerary.getCheckOut()))
        .build();
  }
}
