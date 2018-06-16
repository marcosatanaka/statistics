package br.com.marcosatanaka.statisticsapi.domain;

import java.math.BigDecimal;

public class Statistic {

	private BigDecimal sum;

	private BigDecimal avg;

	private BigDecimal max;

	private BigDecimal min;

	private Integer count;

	public Statistic() {
		this.sum = BigDecimal.valueOf(1.23);
		this.avg = BigDecimal.valueOf(2.34);
		this.max = BigDecimal.valueOf(3.45);
		this.min = BigDecimal.valueOf(4.56);
		this.count = 1;
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

	public Integer getCount() {
		return count;
	}

}
