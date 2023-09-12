package kdt_y_be_toy_project1.trip.dao;

import java.util.List;

import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.dto.TripResponse;
import kdt_y_be_toy_project1.trip.util.CsvConversion;

public class TripCsvDAO implements TripDAO{
	
	private final CsvConversion csvConversion=new CsvConversion();

	public long saveTrip(TripCsv tripCsv) {
		return csvConversion.saveAsCsv(tripCsv);
	}

	@Override
	public List<TripResponse> findAll() {
		return csvConversion.saveAsTrip().stream().map(tripCsvDomain->TripCsvToTripResponse(tripCsvDomain)).toList();
	}
	
	public TripResponse TripCsvToTripResponse(TripCsv tripCsv) {
		if(tripCsv==null)
			throw new RuntimeException("TripCsv 객체가 NULL값입니다.");
		return new TripResponse(tripCsv.getTripId(), tripCsv.getTripName(), tripCsv.getStartDate(), tripCsv.getEndDate());
	}
	
	@Override
	public Long save(Long tripId, String jsonTrip) {
		// TODO Auto-generated method stub
		return null;
	}

}