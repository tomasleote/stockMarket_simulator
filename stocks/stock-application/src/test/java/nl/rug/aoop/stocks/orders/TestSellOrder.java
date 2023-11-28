package nl.rug.aoop.stocks.orders;

import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestSellOrder {
    private Trader trader;
    private Double price;
    private Integer noOfShares;
    private String stock;
    public SellOrder sellOrder;

    @BeforeEach
    void setUp() {
        Portfolio portfolio = mock(Portfolio.class);
        trader = new Trader("id", "John Doe", 50.0, portfolio, 0);
        price = 100.0;
        noOfShares = 10;
        stock = "XYZ";
        sellOrder = new SellOrder(trader, price, noOfShares, stock);
    }

    @Test
    public void testSellOrderInitialisation() {
        assertEquals(trader, sellOrder.getTrader());
        assertEquals(price, sellOrder.getPrice());
        assertEquals(noOfShares, sellOrder.getNoOfShares());
        assertEquals(stock, sellOrder.getStock());
        assertEquals("sell", sellOrder.getType());
    }

    @Test
    public void testFromJson() {
        String json = "{\"trader\":{\"id\":\"id\",\"name\":\"John Doe\",\"funds\":50.0,\"ownedStocks\":{},\"stockPortfolio\":{},\"transactionCount\":0},\"type\":\"sell\",\"price\":100.0,\"noOfShares\":10,\"stock\":\"XYZ\"}";
        SellOrder parseSellOrder = SellOrder.fromJson(json);

        Trader parsedTrader = parseSellOrder.getTrader();
        assertEquals(trader.getId(), parsedTrader.getId());
        assertEquals(trader.getName(), parsedTrader.getName());
        assertEquals(trader.getFunds(), parsedTrader.getFunds(), 0.01);
        assertEquals(trader.getOwnedStocks(), parsedTrader.getOwnedStocks());

        assertEquals(price, parseSellOrder.getPrice());
        assertEquals(noOfShares, parseSellOrder.getNoOfShares());
        assertEquals(stock, parseSellOrder.getStock());
        assertEquals("sell", parseSellOrder.getType());
    }
}
