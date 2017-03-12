package com.jpmorgan.service;

import java.util.ArrayList;

import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;

public interface StockManager {

	public void recordTrade(Trade trade);

	public Stock getStockBySymbol(String stockSymbol) throws Exception;

	public ArrayList<Stock> getStocks();

	public ArrayList<Trade> getTrades();
}
