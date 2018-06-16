package br.com.marcosatanaka.statisticsapi.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class UnixTimestampDeserializer extends JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		String timestamp = jsonParser.getText().trim();
		Instant instant = Instant.ofEpochSecond(Long.parseLong(timestamp));
		return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
	}

}
