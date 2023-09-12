package kdt_y_be_toy_project1.itinerary.controller.dto;

import java.time.LocalDateTime;
import kdt_y_be_toy_project1.itinerary.dto.ItineraryResponse;

public record ItineraryControllerResponse(
    int itineraryId,
    String departurePlace,
    String destination,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    LocalDateTime checkIn,
    LocalDateTime checkOut
) {

    public static ItineraryControllerResponse toControllerResponse(
        ItineraryResponse itineraryResponse) {
        return new ItineraryControllerResponse(itineraryResponse.getItineraryId(),
            itineraryResponse.getDeparturePlace(),
            itineraryResponse.getDestination(), itineraryResponse.getDepartureTime(),
            itineraryResponse.getArrivalTime(), itineraryResponse.getCheckIn(),
            itineraryResponse.getCheckOut());
    }

    @Override
    public String toString() {
        return "%5d  %10s  %10s     %s   %s   %s   %s".formatted(itineraryId, departurePlace,
            destination, departureTime, arrivalTime, checkIn, checkOut);
    }
}
