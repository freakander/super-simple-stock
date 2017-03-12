package com.jpmorgan.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jpmorgan.model.Trade;
import com.jpmorgan.service.StockService;

public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private StockService ss;

	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		ctx.getAutowireCapableBeanFactory().autowireBean(this);
		// SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		// config.getServletContext());
	}

	public Start() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		for (Trade t : ss.getTradeEntries())
			try {
				ss.recordTrade(t);
			} catch (Exception e) {
				e.printStackTrace();
			}

		String[] stockSymbols = { "TEA", "POP", "ALE", "GIN", "JOE" };
		try {
			for (String stockSymbol : stockSymbols) {
				BigDecimal dividendYield = ss.calculateDividendYield(stockSymbol);
				BigDecimal peRatio = ss.calculatePERatio(stockSymbol);
				BigDecimal stockPrice = ss.calculateStockPrice(stockSymbol, 15);
				pw.println("Stock: " + stockSymbol + " Dividend Yield: " + dividendYield
						+ " P/E Ratio: " + peRatio + " Stock Price: " + stockPrice);
			}
			BigDecimal gbce = ss.calculateGBCEAllShareIndex();
			pw.println("GBCE All Share Index: " + gbce);
			pw.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
