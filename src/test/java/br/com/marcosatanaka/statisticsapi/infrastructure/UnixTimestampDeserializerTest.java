package br.com.marcosatanaka.statisticsapi.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnixTimestampDeserializerTest {

	private UnixTimestampDeserializer unixTimestampDeserializer;

	@Before
	public void setUp() {
		this.unixTimestampDeserializer = new UnixTimestampDeserializer();
	}

	@Test
	public void should_deserialize_timestamp_to_utc_zoned_date_time() throws IOException {
		ZonedDateTime now = ZonedDateTime.now();
		JsonParser jsonParser = mockJsonParserWithTimestamp(now);

		ZonedDateTime serializedZonedDateTime = unixTimestampDeserializer.deserialize(jsonParser, null);

		assertThat(serializedZonedDateTime.toEpochSecond()).isEqualTo(now.toEpochSecond());
	}

	private JsonParser mockJsonParserWithTimestamp(ZonedDateTime timestamp) throws IOException {
		UTF8StreamJsonParser jsonParser = mock(UTF8StreamJsonParser.class);
		doReturn(String.valueOf(timestamp.toEpochSecond())).when(jsonParser).getText();
		return jsonParser;
	}

}
