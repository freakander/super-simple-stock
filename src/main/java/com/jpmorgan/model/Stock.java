package com.jpmorgan.model;

import java.math.BigDecimal;

public class Stock {

	public Stock() {
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(BigDecimal tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	private String symbol = null;

	private StockType type = StockType.COMMON;

	private BigDecimal lastDividend = BigDecimal.ZERO;

	private BigDecimal fixedDividend = BigDecimal.ZERO;

	private BigDecimal parValue = BigDecimal.ZERO;

	private BigDecimal tickerPrice = BigDecimal.ZERO;

	public BigDecimal getDividendYield() {
		BigDecimal dividendYield = null;
		if (tickerPrice.compareTo(BigDecimal.ZERO) > 0) {
			switch (type) {
			case COMMON:
				dividendYield = lastDividend.divide(tickerPrice, 3, BigDecimal.ROUND_HALF_UP);
				break;
			case PREFERRED:
				dividendYield = (fixedDividend.multiply(parValue)).divide(tickerPrice, 3, BigDecimal.ROUND_HALF_UP);
				break;
			}
		}
		return dividendYield;
	}

	public BigDecimal getPeRatio() {
		BigDecimal peRatio = null;
		if (tickerPrice.compareTo(BigDecimal.ZERO) > 0) {
			peRatio = tickerPrice.divide(getDividendYield(), 3, BigDecimal.ROUND_HALF_UP);
		}
		return peRatio;
	}
}
