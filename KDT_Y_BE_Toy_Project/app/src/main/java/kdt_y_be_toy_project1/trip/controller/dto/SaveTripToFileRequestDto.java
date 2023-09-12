package kdt_y_be_toy_project1.trip.controller.dto;

import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_FORMATTER;

import java.time.LocalDate;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

public record SaveTripToFileRequestDto(
    String tripName,
    LocalDate startDate,
    LocalDate endDate
) {

    public CreateTripRequest toServiceDto() {
        return new CreateTripRequest(tripName, startDate, endDate);
    }

    public static SaveTripToFileRequestDtoBuilder builder() {
        return new SaveTripToFileRequestDtoBuilder();
    }

    public static class SaveTripToFileRequestDtoBuilder {
        private String tripName;
        private LocalDate startDate;
        private LocalDate endDate;

        public SaveTripToFileRequestDtoBuilder tripName(String tripName) {
            this.tripName = tripName;
            return this;
        }

        public SaveTripToFileRequestDtoBuilder startDate(String startDate) {
            this.startDate = LocalDate.parse(startDate, LOCAL_DATE_FORMATTER);
            return this;
        }

        public SaveTripToFileRequestDtoBuilder endDate(String endDate) {
            this.endDate = LocalDate.parse(endDate, LOCAL_DATE_FORMATTER);
            return this;
        }

        public SaveTripToFileRequestDto build() {
            return new SaveTripToFileRequestDto(tripName, startDate, endDate);
        }

        @Override
        public String toString() {
            return """
                ==========================================================
                                     #  여행 기록  #
                        일정  : %s ~ %s
                        여행명: %s
                ==========================================================
                    """.formatted(startDate, endDate, tripName);
        }
    }
}
