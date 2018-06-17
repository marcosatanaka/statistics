package br.com.marcosatanaka.statisticsapi.domain.statistic;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;

@Service
public class StatisticsService {

	private static final int SIXTY_SECONDS = 60;

	private static final Map<Integer, Statistic> lastMinuteStatistics = new ConcurrentHashMap<>(SIXTY_SECONDS);

	public void computeFor(LogTransactionAction transaction) {
		lastMinuteStatistics.compute(transaction.getSecond(), updatedStatisticsForSecondWith(transaction.getAmount()));
	}

	private BiFunction<Integer, Statistic, Statistic> updatedStatisticsForSecondWith(BigDecimal newAmount) {
		return (key, statisticsForSecond) -> {
			if (isNull(statisticsForSecond) || statisticsForSecond.invalid()) {
				return Statistic.buildWith(newAmount);
			}

			statisticsForSecond.incrementCount();
			statisticsForSecond.incrementSumWith(newAmount);
			return statisticsForSecond;
		};
	}

	public Object get() {
		return null;
	}

}
