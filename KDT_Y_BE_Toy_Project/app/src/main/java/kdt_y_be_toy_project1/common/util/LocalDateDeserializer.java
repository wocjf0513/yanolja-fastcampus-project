package kdt_y_be_toy_project1.common.util;

import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_FORMATTER;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
		JsonParseException {
		return LocalDate.parse(json.getAsString(), LOCAL_DATE_FORMATTER);
	}
}
