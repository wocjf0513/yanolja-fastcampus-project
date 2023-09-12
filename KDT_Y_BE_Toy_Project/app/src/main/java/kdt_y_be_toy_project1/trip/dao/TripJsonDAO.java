package kdt_y_be_toy_project1.trip.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripJsonDAO implements TripDAO {

	private final String BASE_PATH = "src/main/resources/trip/json";

	@Override
	public Long save(Long tripId, String jsonTrip) {
		final Path path = Paths.get(BASE_PATH + "/trip_" + tripId + ".json");

		try {
			Files.createDirectories(path.getParent());
			Files.writeString(path, jsonTrip);

			return tripId;
		} catch (IOException e) {
			throw new RuntimeException("JSON 파일 저장 실패", e);
		}
	}

	@Override
	public List<TripResponse> findAll() {
		List<TripResponse> trips = new ArrayList<>();
		File[] files = getListFiles();

		if (files == null || files.length == 0) {
			throw new RuntimeException("여행 기록이 존재하지 않습니다.");
		}

		for (File file : files) {
			try (Reader tripReader = new FileReader(file)) {
				trips.add(Trip.fromJson(tripReader));
			} catch (IOException e) {
				throw new RuntimeException("JSON 파일 읽기 실패", e);
			}
		}
		return trips;
	}

	public File[] getListFiles() {
		File[] files;
		files = new File(BASE_PATH).listFiles();

		return files;
	}
}
