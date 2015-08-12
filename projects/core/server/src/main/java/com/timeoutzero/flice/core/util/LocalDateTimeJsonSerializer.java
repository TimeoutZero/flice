package com.timeoutzero.flice.core.util;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

	private static final String PATTERN_LOCAL_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
	static DateTimeFormatter formatter = new DateTimeFormatterFactory(PATTERN_LOCAL_DATE_TIME).createDateTimeFormatter();

	@Override
	public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		
		String fomatted = value.toString(formatter);
		jgen.writeString(fomatted);
	}
}
