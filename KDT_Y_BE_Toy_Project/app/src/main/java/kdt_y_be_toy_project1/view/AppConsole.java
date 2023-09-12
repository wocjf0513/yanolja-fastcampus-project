package kdt_y_be_toy_project1.view;


import static kdt_y_be_toy_project1.common.util.input.Precondition.require;
import static kdt_y_be_toy_project1.view.ViewTemplate.BLANK_NOT_REQUIRE;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ARGUMENT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_CORRECT_LOCAL_DATE_TIME_FORMAT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ONLY_JSON_OR_CSV_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ONLY_Y_OR_N_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.KEEP_SAVE_ITINERARY_OR_NOT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SAVE_MENU_HEADER;
import static kdt_y_be_toy_project1.view.ViewTemplate.SAVE_OR_NOT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SELECT_FILE_FORMAT_FOR_SEARCH_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SELECT_MENU_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.TRIP_DETAIL_RESPONSE_HEADER;
import static kdt_y_be_toy_project1.view.ViewTemplate.TRIP_RESPONSE_HEADER;

import java.time.format.DateTimeParseException;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.common.util.input.InputException;
import kdt_y_be_toy_project1.itinerary.controller.ItineraryFileController;
import kdt_y_be_toy_project1.itinerary.controller.dto.FindItinerariesFromFileRequestDto;
import kdt_y_be_toy_project1.itinerary.controller.dto.SaveItineraryToFileRequestDto;
import kdt_y_be_toy_project1.itinerary.controller.dto.SaveItineraryToFileRequestDto.SaveItineraryToFileRequestDtoBuilder;
import kdt_y_be_toy_project1.trip.controller.TripFileController;
import kdt_y_be_toy_project1.trip.controller.dto.SaveTripToFileRequestDto;
import kdt_y_be_toy_project1.trip.controller.dto.SaveTripToFileRequestDto.SaveTripToFileRequestDtoBuilder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class AppConsole {

    private final TripFileController tripFileController;
    private final ItineraryFileController itineraryFileController;
    private final TextOutput output;

    @Getter
    private boolean shutdown;
    private Processor processor;

    public AppConsole() {
        this.tripFileController = new TripFileController();
        this.itineraryFileController = new ItineraryFileController();
        output = new TextOutput();
        processor = getSelectMenuProcessor();
    }

    private Processor getSelectMenuProcessor() {
        output.print(SELECT_MENU_DISPLAY);

        return input -> switch (input) {
            case "1" -> getInsertTripProcessor();
            case "2" -> getInsertItineraryProcessor();
            case "3" -> getSearchTripProcessor();
            case "4" -> getShutdownProcessor();
            default -> getSelectMenuProcessor();
        };
    }

    // CASE 1
    private Processor getInsertTripProcessor() {
        output.println(SAVE_MENU_HEADER.formatted("여행"));
        return getInsertTripStartDateProcessor();
    }

    private Processor getInsertTripStartDateProcessor() {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("시작일(yyyy-MM-dd)"));
        return input -> {
            try {
                return getInsertTripEndDateProcessor(
                    SaveTripToFileRequestDto.builder().startDate(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY);
                return getInsertTripStartDateProcessor();
            }
        };
    }

    private Processor getInsertTripEndDateProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("종료일(yyyy-MM-dd)"));
        return input -> {
            try {
                return getInsertTripNameProcessor(builder.endDate(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY);
                return getInsertTripEndDateProcessor(builder);
            }
        };
    }

    private Processor getInsertTripNameProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행명"));
        return input -> {
            try {
                require(!input.isBlank(), "공백은 허용하지 않습니다.");
                return getSaveOrNotTripProcessor(builder.tripName(input));
            } catch (InputException e) {
                output.println(e.getMessage());
                return getInsertTripNameProcessor(builder);
            }
        };
    }

    private Processor getSaveOrNotTripProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(SAVE_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    return getInsertItineraryProcessor(
                        tripFileController.saveTrip(builder.build()));
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.print(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getSaveOrNotTripProcessor(builder);
                }
            }
        };
    }

    /**
     * It will be run right after getSelectSaveTripFormatProcessor()
     */
    private Processor getInsertItineraryProcessor(long id) {
        output.println(SAVE_MENU_HEADER.formatted("여정"));

        SaveItineraryToFileRequestDtoBuilder builder = SaveItineraryToFileRequestDto.builder();
        return getInsertItineraryDeparturePlaceProcessor(builder.tripId(id));
    }

    // CASE 2

    /**
     * If select 2 in MenuSelectProcessor
     */
    private Processor getInsertItineraryProcessor() {
        output.println(SAVE_MENU_HEADER.formatted("여정"));
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행 ID"));

        return input -> {
            try {
                require(StringUtils.isNumeric(input), "숫자타입을 입력해야 합니다.");
                SaveItineraryToFileRequestDtoBuilder builder = SaveItineraryToFileRequestDto.builder();
                return getInsertItineraryDeparturePlaceProcessor(
                    builder.tripId(Long.parseLong(input)));
            } catch (InputException e) {
                output.println(e.getMessage());
                return getInsertItineraryProcessor();
            }
        };
    }

    private Processor getInsertItineraryDeparturePlaceProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("출발지"));
        return input -> {
            try {
                require(!input.isBlank(), BLANK_NOT_REQUIRE);
                return getInsertItineraryDestinationProcessor(builder.departurePlace(input));
            } catch (InputException e) {
                output.println(e.getMessage());
                return getInsertItineraryDeparturePlaceProcessor(builder);
            }
        };
    }

    private Processor getInsertItineraryDestinationProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("도착지"));
        return input -> {
            try {
                require(!input.isBlank(), BLANK_NOT_REQUIRE);
                return getInsertItineraryDepartureTimeProcessor(builder.destination(input));
            } catch (InputException e) {
                output.println(e.getMessage());
                return getInsertItineraryDestinationProcessor(builder);
            }
        };
    }

    private Processor getInsertItineraryDepartureTimeProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("출발 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> {
            try {
                return getInsertItineraryArrivalTimeProcessor(builder.departureTime(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_TIME_FORMAT_DISPLAY);
                return getInsertItineraryDepartureTimeProcessor(builder);
            }
        };
    }

    private Processor getInsertItineraryArrivalTimeProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("도착 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> {
            try {
                return getInsertItineraryCheckInProcessor(builder.arrivalTime(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_TIME_FORMAT_DISPLAY);
                return getInsertItineraryArrivalTimeProcessor(builder);
            }
        };
    }

    private Processor getInsertItineraryCheckInProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("CheckIn 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> {
            try {
                return getInsertItineraryCheckOutProcessor(builder.checkIn(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_TIME_FORMAT_DISPLAY);
                return getInsertItineraryCheckInProcessor(builder);
            }
        };
    }

    private Processor getInsertItineraryCheckOutProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("CheckOut 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> {
            try {
                return getSaveOrNotItineraryProcessor(builder.checkOut(input));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_TIME_FORMAT_DISPLAY);
                return getInsertItineraryCheckOutProcessor(builder);
            }
        };
    }

    private Processor getSaveOrNotItineraryProcessor(SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(SAVE_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    SaveItineraryToFileRequestDto saveDto = builder.build();

                    itineraryFileController.createItineraryFile(saveDto.tripId());
                    itineraryFileController.saveItinerary(saveDto);
                    return getKeepSaveItineraryOrNotProcessor(saveDto.tripId());
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.println(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getSaveOrNotItineraryProcessor(builder);
                }
            }
        };
    }

    private Processor getKeepSaveItineraryOrNotProcessor(long tripId) {
        output.print(KEEP_SAVE_ITINERARY_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    return getInsertItineraryProcessor(tripId);
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.println(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getKeepSaveItineraryOrNotProcessor(tripId);
                }
            }
        };
    }

    private Processor getSearchTripProcessor() {
        output.print(SELECT_FILE_FORMAT_FOR_SEARCH_DISPLAY);

        return input -> {
            String fileFormat = input.toUpperCase();

            if (isSupportedFileFormat(fileFormat)) {
                output.println(INSERT_ONLY_JSON_OR_CSV_DISPLAY);
                return getSearchTripProcessor();
            }

            output.print(TRIP_RESPONSE_HEADER);
            tripFileController.findAllTripsFromFile(FileFormat.valueOf(fileFormat))
                .forEach(tripResponse -> output.println(tripResponse.toString()));

            return getSearchItinerariesByTripIdProcessor(fileFormat);
        };
    }

    private static boolean isSupportedFileFormat(String fileFormat) {
        return !fileFormat.equals("JSON") && !fileFormat.equals("CSV");
    }

    private Processor getSearchItinerariesByTripIdProcessor(String fileFormat) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행 ID"));

        return input -> {
            output.print(TRIP_DETAIL_RESPONSE_HEADER);
            FindItinerariesFromFileRequestDto requestDto =
                new FindItinerariesFromFileRequestDto(Long.parseLong(input), fileFormat);
            itineraryFileController.findItinerariesByTripId(requestDto)
                .forEach(tripResponse -> output.println(tripResponse.toString()));

            return getSelectMenuProcessor();
        };
    }

    private Processor getShutdownProcessor() {
        shutdownApp();
        return null;
    }

    public void processInput(String input) {
        processor = processor.run(input);
    }

    public String flush() {
        return output.flush();
    }

    private void shutdownApp() {
        shutdown = true;
    }
}
