package br.com.marcosatanaka.statisticsapi.domain.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	private Statistic(BigDecimal amount, Long count, ZonedDateTime timestamp) {
		this.sum = amount;
		this.max = amount;
		this.min = amount;
		this.count = count;
		this.timestamp = timestamp;
	}

	public static Statistic buildWith(BigDecimal amount, ZonedDateTime timestamp) {
		return new Statistic(amount, 1L, timestamp);
	}

	public static Statistic buildEmpty() {
		return new Statistic(BigDecimal.ZERO, 0L, ZonedDateTime.now());
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
		updateMaxValue(newValue);
		updateMinValue(newValue);
	}

	void updateMaxValue(BigDecimal newValue) {
		if (newValue.compareTo(this.max) > 0) {
			this.max = newValue;
		}
	}

	void updateMinValue(BigDecimal newValue) {
		if (newValue.compareTo(this.min) < 0) {
			this.min = newValue;
		}
	}

	public Statistic combine(Statistic anotherStatistic) {
		this.sum = this.sum.add(anotherStatistic.sum);
		this.count += anotherStatistic.count;
		updateMaxValue(anotherStatistic.max);
		updateMinValue(anotherStatistic.min);
		return this;
	}

	public Statistic calculateAverageValueAndReturn() {
		if (count == 0L) {
			this.avg = BigDecimal.ZERO;
			return this;
		}

		this.avg = sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_EVEN);
		return this;
	}

	@JsonIgnore
	public boolean isValid() {
		return Duration.between(timestamp, ZonedDateTime.now()).getSeconds() <= SIXTY_SECONDS;
	}

}
