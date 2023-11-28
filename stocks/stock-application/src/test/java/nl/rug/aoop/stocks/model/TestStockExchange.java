package nl.rug.aoop.stocks.model;

import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.TraderDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestStockExchange {
    private StockExchange stockExchange;

    @BeforeEach
    void setUp() {
        TraderRegistry traderRegistry = new TraderRegistry();
        StockRegistry stockRegistry = new StockRegistry();
        Portfolio portfolio = new Portfolio();
        stockExchange = new StockExchange(traderRegistry, stockRegistry);

         traderRegistry.addTrader(new Trader("id", "name", 100.0, portfolio, 0));
         stockRegistry.addStocks("AAPL", new Stock("AAPL", "Apple", 500L, 150));
    }

    @Test
    void testGetStockByIndex() {
        StockDataModel stock = stockExchange.getStockByIndex(0);
        assertNotNull(stock);
         assertEquals("AAPL", stock.getSymbol());
         assertEquals(150.0, stock.getPrice(), 0.01);
    }

    @Test
    void testGetNumberOfStocks() {
        int numberOfStocks = stockExchange.getNumberOfStocks();
         assertEquals(1, numberOfStocks);
    }

    @Test
    void testGetTraderByIndex() {
        TraderDataModel trader = stockExchange.getTraderByIndex(0);
        assertNotNull(trader);
         assertEquals("name", trader.getName());
         assertEquals(100.0, trader.getFunds(), 0.01);
    }

    @Test
    void testGetNumberOfTraders() {
        int numberOfTraders = stockExchange.getNumberOfTraders();
         assertEquals(1, numberOfTraders);
    }
}
