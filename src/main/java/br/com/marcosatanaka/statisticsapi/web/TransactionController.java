package br.com.marcosatanaka.statisticsapi.web;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import br.com.marcosatanaka.statisticsapi.domain.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity add(@Valid @RequestBody LogTransactionAction transaction) {
		transactionService.add(transaction);
		return new ResponseEntity(HttpStatus.CREATED);
	}

}
