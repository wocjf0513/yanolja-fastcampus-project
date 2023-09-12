package kdt_y_be_toy_project1.trip;

import java.time.LocalDate;

import com.github.javafaker.Faker;

import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

public class TripTestFixture {

	private static final Faker faker = Faker.instance();

	public static CreateTripRequest getCreateTripRequest() {
		return new CreateTripRequest(faker.name().name(), LocalDate.now(), LocalDate.now().plusDays(3));
	}
}
