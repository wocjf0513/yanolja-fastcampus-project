package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripCsvDAO;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripCsvService implements TripService{
	
	private final TripCsvDAO tripCsvDao=new TripCsvDAO();

	@Override
	public Long save(CreateTripRequest tripRequestDto) {
		return tripCsvDao.saveTrip(TripCsv.fromTripRequest(tripRequestDto));
	}

	@Override
	public List<TripResponse> findAll() {
		return tripCsvDao.findAll();
	}

}
