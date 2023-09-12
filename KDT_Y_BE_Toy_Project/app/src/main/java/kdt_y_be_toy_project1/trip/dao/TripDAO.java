package kdt_y_be_toy_project1.trip.dao;

import java.util.List;

import kdt_y_be_toy_project1.trip.dto.TripResponse;

public interface TripDAO {

	Long save(Long tripId, String jsonTrip);

	List<TripResponse> findAll();
}
