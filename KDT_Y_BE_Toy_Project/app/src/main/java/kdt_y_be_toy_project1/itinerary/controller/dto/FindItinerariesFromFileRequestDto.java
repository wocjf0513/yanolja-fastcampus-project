package kdt_y_be_toy_project1.itinerary.controller.dto;

import java.util.Objects;
import kdt_y_be_toy_project1.common.util.FileFormat;
import lombok.Getter;

@Getter
public class FindItinerariesFromFileRequestDto {

    private final long tripId;
    private final FileFormat fileFormat;

    public FindItinerariesFromFileRequestDto(Long tripId, String fileFormat) {
        this.tripId = Objects.requireNonNull(tripId);
        this.fileFormat = FileFormat.valueOf(fileFormat);
    }
}
