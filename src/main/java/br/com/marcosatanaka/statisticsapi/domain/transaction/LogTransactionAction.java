package br.com.marcosatanaka.statisticsapi.domain.transaction;

import br.com.marcosatanaka.statisticsapi.infrastructure.UnixTimestampDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class LogTransactionAction {

	private BigDecimal amount;

	@JsonDeserialize(using = UnixTimestampDeserializer.class)
	private ZonedDateTime timestamp;

	protected LogTransactionAction() {
		// For JSON deserialization
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

}
