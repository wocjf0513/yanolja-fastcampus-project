package kdt_y_be_toy_project1.trip.dto;

import java.time.LocalDate;

public record CreateTripRequest(String tripName, LocalDate startDate, LocalDate endDate) {
}
