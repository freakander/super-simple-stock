package com.jpmorgan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import static org.apache.commons.math3.stat.StatUtils.geometricMean;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import com.jpmorgan.service.StockManager;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;
import com.jpmorgan.service.StockService;

public class StockServiceImpl implements StockService {

	private ArrayList<Trade> tradeEntries = null;

	private StockManager sm = null;

	public ArrayList<Trade> getTradeEntries() {
		return tradeEntries;
	}

	public void setTradeEntries(ArrayList<Trade> trades) {
		this.tradeEntries = trades;
	}

	public void setStockManager(StockManager sm) {
		this.sm = sm;
	}

	public BigDecimal calculateDividendYield(String stockSymbol) throws Exception {
		BigDecimal dividendYield = BigDecimal.ONE.negate();
		Stock stock = sm.getStockBySymbol(stockSymbol);
		dividendYield = stock.getDividendYield();
		return dividendYield;
	}

	public BigDecimal calculatePERatio(String stockSymbol) throws Exception {
		BigDecimal peRatio = BigDecimal.ONE.negate();
		Stock stock = sm.getStockBySymbol(stockSymbol);
		peRatio = stock.getPeRatio();
		return peRatio;
	}

	public void recordTrade(Trade trade) throws Exception {
		try {
			if (trade.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception("Shares quantity should be greater than zero.");
			}
			if (trade.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception("Shares price should be greater than zero.");
			}
			sm.recordTrade(trade);

		} catch (Exception exception) {
			throw new Exception("Error when trying to record a trade.", exception);
		}
	}

	public BigDecimal calculateStockPrice(final String stockSymbol, final int minutesRange) throws Exception {
		BigDecimal stockPrice = BigDecimal.ZERO;
		Collection<Trade> trades = Collections2.filter(sm.getTrades(), new Predicate<Trade>() {
			public boolean apply(Trade input) {
				Calendar dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
				boolean shouldBeInclude = input.getStock().getSymbol().equals(stockSymbol);
				if (shouldBeInclude && dateRange != null) {
					shouldBeInclude = dateRange.getTime().compareTo(input.getTimeStamp()) <= 0;
				}
				return shouldBeInclude;
			}
		});
		BigDecimal qtyTotal = BigDecimal.ZERO;
		BigDecimal costTotal = BigDecimal.ZERO;
		for (Trade trade : trades) {
			costTotal = costTotal.add(trade.getPrice().multiply(trade.getQuantity()));
			qtyTotal = qtyTotal.add(trade.getQuantity());
		}
		if (qtyTotal.compareTo(BigDecimal.ZERO) > 0) {
			stockPrice = costTotal.divide(qtyTotal, 3, BigDecimal.ROUND_HALF_UP);
		}
		return stockPrice;
	}

	public BigDecimal calculateGBCEAllShareIndex() throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		ArrayList<Stock> stocks = sm.getStocks();
		double[] stockPrices = new double[stocks.size()];
		int i = 0;
		for (Stock stock : stocks) {
			BigDecimal stockPrice = calculateStockPrice(stock.getSymbol(), 15);
			if (stockPrice.compareTo(BigDecimal.ZERO) > 0) {
				stockPrices[i] = stockPrice.doubleValue();
				i++;
			}
		}

		result = BigDecimal.valueOf(geometricMean(stockPrices));
		return result;
	}
}