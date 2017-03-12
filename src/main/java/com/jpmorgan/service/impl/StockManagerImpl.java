package com.jpmorgan.service.impl;

import java.util.ArrayList;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import com.jpmorgan.service.StockManager;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.Trade;

public class StockManagerImpl implements StockManager {

	private ArrayList<Stock> stocks = null;
	private ArrayList<Trade> trades = null;

	public StockManagerImpl(){
		trades = new ArrayList<Trade>();
		stocks = new ArrayList<Stock>();
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}

	public ArrayList<Trade> getTrades() {
		return trades;
	}

	public void setTrades(ArrayList<Trade> trades) {
		this.trades = trades;
	}

	public void recordTrade(Trade trade) {
		trades.add(trade);
		trade.getStock().setTickerPrice(trade.getPrice());
	}

	public Stock getStockBySymbol(final String stockSymbol) throws Exception {
		Stock stock = null;
		if(stockSymbol!=null){
			stock = Lists.newArrayList(
			Collections2.filter(stocks, new Predicate<Stock>() {
				public boolean apply (Stock input) {
					return input.getSymbol().equals(stockSymbol);
				}
			})).get(0);
		}
		if (stock == null) {
			throw new Exception("The stock symbol: " + stockSymbol + " is not supported.");
		}
		return stock;
	}
}
