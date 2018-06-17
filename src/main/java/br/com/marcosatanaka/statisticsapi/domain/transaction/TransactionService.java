package br.com.marcosatanaka.statisticsapi.domain.transaction;

import br.com.marcosatanaka.statisticsapi.domain.statistic.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	private final StatisticsService statisticsService;

	@Autowired
	public TransactionService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public void add(LogTransactionAction transaction) {
		statisticsService.computeFor(transaction);
	}

}
