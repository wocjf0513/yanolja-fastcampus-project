package kdt_y_be_toy_project1.trip.service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripJsonDAO;
import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripJsonService implements TripService {

	private final TripJsonDAO tripJsonDAO = new TripJsonDAO();

	@Override
	public Long save(CreateTripRequest dto) {
		Trip trip = Trip.from(nextTripId(), dto.tripName(), dto.startDate(), dto.endDate());
		return tripJsonDAO.save(trip.getTripId(), trip.toJson(trip));
	}

	@Override
	public List<TripResponse> findAll() {
		return tripJsonDAO.findAll();
	}

	protected Long nextTripId() {
		File[] files = tripJsonDAO.getListFiles();

		if (files == null || files.length == 0) {
			return 1L;
		}
		String lastFileName = Arrays.stream(files)
			.max(Comparator.comparing(file -> file.lastModified()
			)).orElseThrow().getName();

		int startIndex = lastFileName.lastIndexOf("_");
		int endIndex = lastFileName.lastIndexOf(".json");

		return Long.parseLong(lastFileName.substring(startIndex + 1, endIndex)) + 1;
	}
}
