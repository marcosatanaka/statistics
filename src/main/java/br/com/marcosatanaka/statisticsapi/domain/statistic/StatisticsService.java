package br.com.marcosatanaka.statisticsapi.domain.statistic;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;

@Service
public class StatisticsService {

	static final int SIXTY_SECONDS = 60;

	private static final Map<Integer, Statistic> lastMinuteStatistics = new ConcurrentHashMap<>(SIXTY_SECONDS);

	public void computeFor(LogTransactionAction transaction) {
		lastMinuteStatistics.compute(transaction.getSecond(), updatedStatisticForSecondWith(transaction));
	}

	private BiFunction<Integer, Statistic, Statistic> updatedStatisticForSecondWith(LogTransactionAction newTransaction) {
		return (key, statisticForSecond) -> {
			if (isNull(statisticForSecond) || !statisticForSecond.isValid()) {
				return Statistic.buildWith(newTransaction.getAmount(), newTransaction.getTimestamp());
			}

			statisticForSecond.incrementCount();
			statisticForSecond.incrementSumWith(newTransaction.getAmount());
			return statisticForSecond;
		};
	}

	public Statistic get() {
		return lastMinuteStatistics.values()
				.stream()
				.filter(Statistic::isValid)
				.reduce(Statistic::combine)
				.orElse(Statistic.buildEmpty())
				.calculateAverageValueAndReturn();
	}

}
