package kdt_y_be_toy_project1.trip.domain;

import com.opencsv.bean.CsvBindByName;

import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCsv {
	@CsvBindByName(column = "trip_id")
	private long tripId;
	@CsvBindByName(column = "trip_name")
	private String tripName;
	@CsvBindByName(column = "start_date")
	private String startDate;
	@CsvBindByName(column = "end_date")
	private String endDate;
	
	//open csv mapping 함수를 사용하기 위해 stripng date를 사용!

	
	public static TripCsv fromTripRequest(CreateTripRequest tripRequest) {
		return TripCsv.builder().tripName(tripRequest.tripName())
				.startDate(tripRequest.startDate().toString()).endDate(tripRequest.endDate().toString()).build();
	}
	public static TripCsv fromTripResponse(TripResponse tripResponse) {
		return TripCsv.builder().tripId(tripResponse.tripId()).tripName(tripResponse.tripName())
				.startDate(tripResponse.startDate()).endDate(tripResponse.endDate()).build();
	}
}