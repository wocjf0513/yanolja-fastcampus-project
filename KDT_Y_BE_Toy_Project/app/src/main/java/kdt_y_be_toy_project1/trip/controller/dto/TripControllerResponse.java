package kdt_y_be_toy_project1.trip.controller.dto;

import kdt_y_be_toy_project1.trip.dto.TripResponse;

public record TripControllerResponse(
    Long tripId,
    String tripName,
    String startDate,
    String endDate) {


    public static TripControllerResponse toControllerResponse(TripResponse tripResponse) {
        return new TripControllerResponse(tripResponse.tripId(),
            tripResponse.tripName(),
            tripResponse.startDate(),
            tripResponse.endDate());
    }

    @Override
    public String toString() {
        String shortenTripName = tripName;
        int length = tripName.length();
        if (length > 10) {
            shortenTripName = tripName.substring(0, 10) + "..";
        }
        return "%5d      %15s          %s     %s".formatted(
            tripId, shortenTripName, startDate, endDate
        );
    }
}
