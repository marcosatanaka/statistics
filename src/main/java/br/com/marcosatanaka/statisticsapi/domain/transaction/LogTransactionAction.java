package br.com.marcosatanaka.statisticsapi.domain.transaction;

import br.com.marcosatanaka.statisticsapi.infrastructure.UnixTimestampDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

public class LogTransactionAction {

	private static final int SIXTY_SECONDS = 60;

	@NotNull(message = "Amount cannot be null")
	private BigDecimal amount;

	@NotNull(message = "Timestamp cannot be null")
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

	public int getSecond() {
		return getTimestamp().getSecond();
	}

	public boolean isOlderThanSixtySeconds() {
		return Duration.between(getTimestamp(), ZonedDateTime.now()).getSeconds() > SIXTY_SECONDS;
	}

}
