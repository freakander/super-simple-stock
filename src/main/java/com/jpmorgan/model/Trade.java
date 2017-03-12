package com.jpmorgan.model;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {
	private Date timeStamp = null;
	private Stock stock = null;
	private TradeIndicator tradeIndicator = TradeIndicator.BUY;

	private BigDecimal quantity = BigDecimal.ZERO;

	private BigDecimal price = BigDecimal.ZERO;

	public Trade() {
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public TradeIndicator getTradeIndicator() {
		return tradeIndicator;
	}

	public void setTradeIndicator(TradeIndicator tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
