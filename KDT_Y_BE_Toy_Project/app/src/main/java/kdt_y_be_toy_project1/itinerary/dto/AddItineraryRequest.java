package kdt_y_be_toy_project1.itinerary.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class AddItineraryRequest {
  private int itineraryId;
  private String departurePlace;
  private String destination;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private LocalDateTime checkIn;
  private LocalDateTime checkOut;
}
