package br.com.marcosatanaka.statisticsapi.domain.statistic;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.*;
import static java.time.ZonedDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;

public class StatisticTest {

	@Test
	public void should_build_statistic_with_parameters() {
		BigDecimal amount = BigDecimal.valueOf(123.45);
		ZonedDateTime timestamp = now();
		Statistic statistic = Statistic.buildWith(amount, timestamp);

		assertThat(statistic.getSum()).isEqualTo(amount);
		assertThat(statistic.getMax()).isEqualTo(amount);
		assertThat(statistic.getMin()).isEqualTo(amount);
		assertThat(statistic.getCount()).isEqualTo(1L);
	}

	@Test
	public void should_build_empty_statistic() {
		Statistic statistic = Statistic.buildEmpty();

		assertThat(statistic.getSum()).isEqualTo(BigDecimal.ZERO);
		assertThat(statistic.getMax()).isEqualTo(BigDecimal.ZERO);
		assertThat(statistic.getMin()).isEqualTo(BigDecimal.ZERO);
		assertThat(statistic.getCount()).isEqualTo(0L);
	}

	@Test
	public void should_increment_count() {
		Statistic statistic = Statistic.buildEmpty();
		statistic.incrementCount();
		statistic.incrementCount();
		statistic.incrementCount();
		assertThat(statistic.getCount()).isEqualTo(3L);
	}

	@Test
	public void should_increment_sum_and_update_min_max_values() {
		Statistic statistic = Statistic.buildEmpty();
		statistic.incrementSumWith(BigDecimal.valueOf(10));
		statistic.incrementSumWith(BigDecimal.valueOf(20));

		assertThat(statistic.getSum()).isEqualTo(BigDecimal.valueOf(30));
		assertThat(statistic.getMax()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(statistic.getMin()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	public void should_update_min_value() {
		Statistic statistic1 = Statistic.buildWith(BigDecimal.valueOf(123.45), now());
		Statistic statistic2 = Statistic.buildWith(BigDecimal.valueOf(123.45), now());

		statistic1.updateMinValue(BigDecimal.valueOf(123.44));
		statistic2.updateMinValue(BigDecimal.valueOf(123.46));

		assertThat(statistic1.getMin()).isEqualTo(BigDecimal.valueOf(123.44));
		assertThat(statistic2.getMin()).isEqualTo(BigDecimal.valueOf(123.45));
	}

	@Test
	public void should_update_max_value() {
		Statistic statistic1 = Statistic.buildWith(BigDecimal.valueOf(123.45), now());
		Statistic statistic2 = Statistic.buildWith(BigDecimal.valueOf(123.45), now());

		statistic1.updateMaxValue(BigDecimal.valueOf(123.46));
		statistic2.updateMaxValue(BigDecimal.valueOf(123.44));

		assertThat(statistic1.getMax()).isEqualTo(BigDecimal.valueOf(123.46));
		assertThat(statistic2.getMax()).isEqualTo(BigDecimal.valueOf(123.45));
	}

	@Test
	public void should_calculate_average_value_zero() {
		Statistic statistic = Statistic.buildEmpty().calculateAverageValueAndReturn();
		assertThat(statistic.getAvg()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	public void should_be_valid() {
		Statistic statisticOlderThanOneMinute = Statistic.buildWith(BigDecimal.TEN, now().minus(65, SECONDS));
		Statistic statisticYoungerThanOneMinute = Statistic.buildWith(BigDecimal.TEN, now().minus(55, SECONDS));

		assertThat(statisticOlderThanOneMinute.isValid()).isFalse();
		assertThat(statisticYoungerThanOneMinute.isValid()).isTrue();
	}

}
