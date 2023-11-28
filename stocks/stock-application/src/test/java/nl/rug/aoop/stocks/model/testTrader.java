package nl.rug.aoop.stocks.model;

import nl.rug.aoop.messagequeue.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class testTrader {
    private Trader trader;

    @BeforeEach
    void setUp() {
       trader = new Trader("1", "John Doe", 1000.0, new Portfolio(), 1);
    }

    @Test
    void testGetOwnedStocks() {
        assertEquals(0, trader.getOwnedStocks().size());
        trader.buy("AAPL", 10);
        trader.buy("GOOGL", 5);
        assertEquals(2, trader.getOwnedStocks().size());
    }

    @Test
    void testGetOwnedStocksInPortfolio() {
        trader.buy("AAPL", 10);
        trader.buy("GOOGL", 5);
        assertEquals(10, trader.getOwnedStocksInPortfolio().get("AAPL"));
        assertEquals(5, trader.getOwnedStocksInPortfolio().get("GOOGL"));
    }

    @Test
    void testSell() {
        trader.buy("AAPL", 10);
        trader.sell("AAPL", 5);
        int amount = trader.getOwnedStocksInPortfolio().get("AAPL");
        assertEquals(5, amount);
    }

    @Test
    void testBuy() {
        trader.buy("AAPL", 10);
        assertEquals(10, trader.getOwnedStocksInPortfolio().get("AAPL"));
    }

    @Test
    void testToJson() {
        String json = trader.toJson();
        Trader newTrader = Trader.fromJson(json);
        assertEquals(trader.getId(), newTrader.getId());
        assertEquals(trader.getName(), newTrader.getName());
        assertEquals(trader.getFunds(), newTrader.getFunds());
        assertEquals(trader.getStockPortfolio().getOwnedShares(), newTrader.getStockPortfolio().getOwnedShares());
    }
}

