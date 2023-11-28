package nl.rug.aoop.stocks.model;

import nl.rug.aoop.messagequeue.Message;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStock {

    @Test
    public void testGetMarketCap() {
        Stock stock = new Stock("AMZN", "Amazon.com Inc", 750000L, 2000.25);
        assertEquals(1500187500, stock.getMarketCap(), 0.001);
    }

    @Test
    public void testSetPrice() {
        Stock stock = new Stock("MSFT", "Microsoft Corporation", 1000000L, 300.60);
        stock.setPrice(310.75);
        assertEquals(310.75, stock.getPrice(), 0.001);
    }

    @Test
    public void testToMessage() {
        Stock stock = new Stock("TSLA", "Tesla Inc", 300000L, 900.20);
        Message expectedMessage = new Message("TSLA", new Message("Tesla Inc", new Message("300000", "900.2").toJson()).toJson());
        Stock msgStockModel = new Stock(expectedMessage);
        assertEquals(stock.getInitialPrice(), msgStockModel.getInitialPrice(), 0.01);
        assertEquals(stock.getMarketCap(), msgStockModel.getMarketCap(), 0.01);
        assertEquals(stock.getPrice(), msgStockModel.getPrice(), 0.01);
        assertEquals(stock.getSharesOutstanding(), msgStockModel.getSharesOutstanding());
        assertEquals(stock.getSymbol(), msgStockModel.getSymbol());

    }
}
