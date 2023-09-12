package kdt_y_be_toy_project1.itinerary.controller.dto;

import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;

public record SaveItineraryToFileRequestDto(
    long tripId,
    String departurePlace,
    String destination,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    LocalDateTime checkIn,
    LocalDateTime checkOut
) {

    public AddItineraryRequest toServiceDto() {
        return AddItineraryRequest.builder()
            .departurePlace(departurePlace)
            .destination(destination)
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .checkIn(checkIn)
            .checkOut(checkOut)
            .build();
    }

    public static SaveItineraryToFileRequestDtoBuilder builder() {
        return new SaveItineraryToFileRequestDtoBuilder();
    }

    public static class SaveItineraryToFileRequestDtoBuilder {
        private long tripId;
        private String departurePlace;
        private String destination;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;

        public SaveItineraryToFileRequestDtoBuilder tripId(long tripId) {
            this.tripId = tripId;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder departurePlace(String departurePlace) {
            this.departurePlace = departurePlace;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder departureTime(String departureTime) {
            this.departureTime = LocalDateTime.parse(departureTime, LOCAL_DATE_TIME_FORMATTER);
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder arrivalTime(String arrivalTime) {
            this.arrivalTime = LocalDateTime.parse(arrivalTime, LOCAL_DATE_TIME_FORMATTER);
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder checkIn(String checkIn) {
            this.checkIn = LocalDateTime.parse(checkIn, LOCAL_DATE_TIME_FORMATTER);
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder checkOut(String checkOut) {
            this.checkOut = LocalDateTime.parse(checkOut, LOCAL_DATE_TIME_FORMATTER);
            return this;
        }

        public SaveItineraryToFileRequestDto build() {
            return new SaveItineraryToFileRequestDto(tripId, departurePlace, destination,
                departureTime, arrivalTime, checkIn, checkOut);
        }

        @Override
        public String toString() {
            return """
                ==========================================================
                                     #  여정 기록  #
                        출  발  지 : %s
                        도  착  지 : %s
                        출발 시간 ~ 도착 시간 : %s ~ %s
                        체 크 인   ~ 체크 아웃 : %s ~ %s
                ==========================================================
                    """.formatted(departurePlace, destination, departureTime, arrivalTime, checkIn, checkOut);
        }
    }
}
