package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public interface TripService {

	Long save(CreateTripRequest tripRequestDto);

	List<TripResponse> findAll();
}
