package kdt_y_be_toy_project1.trip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kdt_y_be_toy_project1.trip.TripTestFixture;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

class TripJsonServiceTest {

	private final TripService tripService = new TripJsonService();

	@Test
	@DisplayName("여행 정보 저장")
	public void 여행_기록() {
		// given
		CreateTripRequest createTripRequest = TripTestFixture.getCreateTripRequest();

		// when
		Long tripId = tripService.save(createTripRequest);
		List<TripResponse> trips = tripService.findAll()
			.stream()
			.sorted((trip1, trip2) -> Long.compare(trip2.tripId(), trip1.tripId()))
			.collect(Collectors.toList());

		// then
		Assertions.assertThat(tripId).isEqualTo(trips.get(0).tripId());
	}

	@Test
	@DisplayName("여행 정보 조회")
	public void 여행_조회() {
		// given

		// when
		List<TripResponse> trips = tripService.findAll();

		// then
		System.out.println(trips);
	}

	@Test
	@DisplayName("새로 등록될 여행 ID 생성")
	public void 여행_아이디_생성() {
		// given
		NextTripIdTest nextTripIdTest = new NextTripIdTest();
		// when
		Long nextTripId = nextTripIdTest.callNextTripId();

		// then
		System.out.println(nextTripId);
	}
}
