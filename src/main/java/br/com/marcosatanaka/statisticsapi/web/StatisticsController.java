package br.com.marcosatanaka.statisticsapi.web;

import br.com.marcosatanaka.statisticsapi.domain.Statistic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@GetMapping
	public ResponseEntity get() {
		return ResponseEntity.ok(new Statistic());
	}

}
