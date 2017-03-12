package com.jpmorgan.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.jpmorgan.model.Trade;

public interface StockService {

	public BigDecimal calculateDividendYield(String stockSymbol) throws Exception;

	public BigDecimal calculatePERatio(String stockSymbol) throws Exception;

	public void recordTrade(Trade trade) throws Exception;

	public BigDecimal calculateStockPrice(final String stockSymbol, final int minutesRange) throws Exception;

	public BigDecimal calculateGBCEAllShareIndex() throws Exception;

	public ArrayList<Trade> getTradeEntries();
}
