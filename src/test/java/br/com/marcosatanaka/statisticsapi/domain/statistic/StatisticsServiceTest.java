package br.com.marcosatanaka.statisticsapi.domain.statistic;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;

import static br.com.marcosatanaka.statisticsapi.domain.statistic.StatisticsService.SIXTY_SECONDS;
import static java.time.ZonedDateTime.*;
import static java.time.temporal.ChronoUnit.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

	@InjectMocks
	private StatisticsService statisticsService;

	/**
	 * Ok, this is very ugly, but I need to reset the static
	 * map which holds the statistics for the last minute.
	 * Otherwise one test is going to affect the other.
	 */
	@Before
	public void setUp() throws NoSuchFieldException, IllegalAccessException {
		Field field = StatisticsService.class.getDeclaredField("lastMinuteStatistics");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, new ConcurrentHashMap<>(SIXTY_SECONDS));
	}

	@Test
	public void should_compute_last_minute_statistics() {
		Integer maxSecondsTest = 50; // Safety margin

		for (int i = maxSecondsTest; i >= 1; i--) {
			statisticsService.computeFor(mock(BigDecimal.valueOf(i), now().minus(i, SECONDS)));
			statisticsService.computeFor(mock(BigDecimal.valueOf(i), now().minus(i, SECONDS)));
		}

		Statistic lastMinuteStatistics = statisticsService.get();
		assertThat(lastMinuteStatistics.getCount()).isEqualTo(Long.valueOf(maxSecondsTest * 2));
		assertThat(lastMinuteStatistics.getSum()).isEqualTo(BigDecimal.valueOf(2550));
		assertThat(lastMinuteStatistics.getMax()).isEqualTo(BigDecimal.valueOf(50));
		assertThat(lastMinuteStatistics.getMin()).isEqualTo(BigDecimal.valueOf(1));
		assertThat(lastMinuteStatistics.getAvg()).isEqualTo(BigDecimal.valueOf(2550).divide(BigDecimal.valueOf(maxSecondsTest * 2), RoundingMode.HALF_EVEN));
	}

	@Test
	public void old_transactions_should_not_affect_last_minute_statistics() {
		statisticsService.computeFor(mock(BigDecimal.valueOf(900000), now().minus(99, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(900000), now().minus(61, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(166.11), now().minus(24, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(287.88), now().minus(20, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(216.91), now().minus(18, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(193.62), now().minus(16, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(302.60), now().minus(14, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(911.71), now().minus(12, SECONDS)));
		statisticsService.computeFor(mock(BigDecimal.valueOf(472.79), now().minus(10, SECONDS)));

		Statistic statistics = statisticsService.get();
		assertThat(statistics.getSum()).isEqualTo(BigDecimal.valueOf(2551.62));
		assertThat(statistics.getMax()).isEqualTo(BigDecimal.valueOf(911.71));
		assertThat(statistics.getMin()).isEqualTo(BigDecimal.valueOf(166.11));
		assertThat(statistics.getAvg()).isEqualTo(BigDecimal.valueOf(364.52));
	}

	private LogTransactionAction mock(BigDecimal amount, ZonedDateTime timestamp) {
		LogTransactionAction logTransactionAction = Mockito.mock(LogTransactionAction.class);
		doReturn(amount).when(logTransactionAction).getAmount();
		doReturn(timestamp).when(logTransactionAction).getTimestamp();
		return logTransactionAction;
	}

}
