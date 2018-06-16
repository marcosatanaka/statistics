package br.com.marcosatanaka.statisticsapi.web;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@PostMapping
	public ResponseEntity create(@RequestBody LogTransactionAction transaction) {
		return new ResponseEntity(HttpStatus.CREATED);
	}

}
