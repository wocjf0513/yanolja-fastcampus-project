package kdt_y_be_toy_project1.itinerary.controller;

import java.util.List;
import kdt_y_be_toy_project1.itinerary.controller.dto.FindItinerariesFromFileRequestDto;
import kdt_y_be_toy_project1.itinerary.controller.dto.ItineraryControllerResponse;
import kdt_y_be_toy_project1.itinerary.controller.dto.SaveItineraryToFileRequestDto;
import kdt_y_be_toy_project1.itinerary.service.ItineraryService;

public class ItineraryFileController {

    private final ItineraryService itineraryService = new ItineraryService();

    public void createItineraryFile(long tripId) {
        itineraryService.createItinerary(tripId);
    }

    public void saveItinerary(SaveItineraryToFileRequestDto request) {

        itineraryService.addItinerary(request.tripId(), request.toServiceDto());
    }

    public List<ItineraryControllerResponse> findItinerariesByTripId(
        FindItinerariesFromFileRequestDto request) {
        return itineraryService.getAllItineraryList(request.getTripId(), request.getFileFormat())
            .stream()
            .map(ItineraryControllerResponse::toControllerResponse)
            .toList();
    }
}
