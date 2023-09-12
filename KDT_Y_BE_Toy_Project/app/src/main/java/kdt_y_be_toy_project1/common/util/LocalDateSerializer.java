package kdt_y_be_toy_project1.common.util;

import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_FORMATTER;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {

	@Override
	public JsonElement serialize(LocalDate localDate, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(LOCAL_DATE_FORMATTER.format(localDate));
	}
}
