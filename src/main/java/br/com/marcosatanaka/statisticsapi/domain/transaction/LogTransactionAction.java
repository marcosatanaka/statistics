package br.com.marcosatanaka.statisticsapi.domain.transaction;

import br.com.marcosatanaka.statisticsapi.infrastructure.UnixTimestampDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

public class LogTransactionAction {

	private static final int SIXTY_SECONDS = 60;

	public static final String MSG_AMOUNT_CANNOT_BE_NULL = "Amount cannot be null";

	public static final String MSG_TIMESTAMP_CANNOT_BE_NULL = "Timestamp cannot be null";

	@NotNull(message = MSG_AMOUNT_CANNOT_BE_NULL)
	private BigDecimal amount;

	@NotNull(message = MSG_TIMESTAMP_CANNOT_BE_NULL)
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
