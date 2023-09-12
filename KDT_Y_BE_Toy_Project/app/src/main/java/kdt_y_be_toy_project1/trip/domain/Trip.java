package kdt_y_be_toy_project1.trip.domain;

import java.io.Reader;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kdt_y_be_toy_project1.common.util.LocalDateDeserializer;
import kdt_y_be_toy_project1.common.util.LocalDateSerializer;
import kdt_y_be_toy_project1.trip.dto.TripResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Trip {

	private Long tripId;

	private String tripName;

	private LocalDate startDate;

	private LocalDate endDate;

	public static Trip from(Long tripId, String tripName, LocalDate startDate, LocalDate endDate) {
		return Trip.builder()
			.tripId(tripId)
			.tripName(tripName)
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}

	public String toJson(Trip trip) {
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
			.create();

		return gson.toJson(trip);
	}

	public static TripResponse fromJson(Reader tripReader) {
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
			.create();

		return gson.fromJson(tripReader, TripResponse.class);
	}
}
