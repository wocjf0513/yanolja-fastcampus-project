package kdt_y_be_toy_project1.itinerary.controller.dto;

import java.util.Objects;
import kdt_y_be_toy_project1.common.util.FileFormat;
import lombok.Getter;

@Getter
public class FindItineraryFromFileRequestDto {

    private final long tripId;
    private final int itineraryId;
    private final FileFormat fileFormat;

    public FindItineraryFromFileRequestDto(Long tripId, Integer itineraryId, String fileFormat) {
        this.tripId = Objects.requireNonNull(tripId);
        this.itineraryId = Objects.requireNonNull(itineraryId);
        this.fileFormat = FileFormat.valueOf(fileFormat);
    }
}
