package kdt_y_be_toy_project1.common.util;

import java.time.format.DateTimeFormatter;

public class DateBindingFormatter {

    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateBindingFormatter() {
    }
}
