package br.com.marcosatanaka.statisticsapi.domain.statistic;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

public class Statistic {

	private static final int SIXTY_SECONDS = 60;

	private BigDecimal sum;

	private BigDecimal avg;

	private BigDecimal max;

	private BigDecimal min;

	private Long count;

	private ZonedDateTime timestamp;

	private Statistic(BigDecimal amount) {
		this.sum = amount;
		this.max = amount;
		this.min = amount;
		this.count = 1L;
		this.timestamp = ZonedDateTime.now();
	}

	public static Statistic buildWith(BigDecimal amount) {
		return new Statistic(amount);
	}

	public BigDecimal getSum() {
		return sum;
	}

	public BigDecimal getAvg() {
		return avg;
	}

	public BigDecimal getMax() {
		return max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public Long getCount() {
		return count;
	}

	public void incrementCount() {
		this.count += 1;
}

	public void incrementSumWith(BigDecimal newValue) {
		this.sum = this.sum.add(newValue);

		if (newValue.compareTo(this.max) > 0) {
			this.max = newValue;
		}

		if (newValue.compareTo(this.min) < 0) {
			this.min = newValue;
		}
	}

	public boolean invalid() {
		return Duration.between(timestamp, ZonedDateTime.now()).getSeconds() > SIXTY_SECONDS;
	}

}
