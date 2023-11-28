package nl.rug.aoop.stocks.orders;

import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TestBuyOrder {

    private Trader trader;
    private Double price;
    private Integer noOfShares;
    private String stock;
    private BuyOrder buyOrder;

    @BeforeEach
    public void setUp() {
        Portfolio portfolio = mock(Portfolio.class);
        trader = new Trader("id", "John Doe", 50.0, portfolio, 0);
        price = 100.0;
        noOfShares = 10;
        stock = "XYZ";
        buyOrder = new BuyOrder(trader, price, noOfShares, stock);
    }

    @Test
    public void testBuyOrderInitialization() {
        assertEquals(trader, buyOrder.getTrader());
        assertEquals(price, buyOrder.getPrice());
        assertEquals(noOfShares, buyOrder.getNoOfShares());
        assertEquals(stock, buyOrder.getStock());
        assertEquals("buy", buyOrder.getType());
    }

    @Test
    public void testFromJson() {
        String json = "{\"trader\":{\"id\":\"id\",\"name\":\"John Doe\",\"funds\":50.0,\"ownedStocks\":{},\"stockPortfolio\":{},\"transactionCount\":0},\"type\":\"buy\",\"price\":100.0,\"noOfShares\":10,\"stock\":\"XYZ\"}";
        BuyOrder parsedBuyOrder = BuyOrder.fromJson(json);
        assertNotNull(parsedBuyOrder);

        // Verify Trader object inside BuyOrder
        Trader parsedTrader = parsedBuyOrder.getTrader();
        assertEquals(trader.getId(), parsedTrader.getId());
        assertEquals(trader.getName(), parsedTrader.getName());
        assertEquals(trader.getFunds(), parsedTrader.getFunds(), 0.01);
        assertEquals(trader.getOwnedStocks(), parsedTrader.getOwnedStocks());

        assertEquals(price, parsedBuyOrder.getPrice());
        assertEquals(noOfShares, parsedBuyOrder.getNoOfShares());
        assertEquals(stock, parsedBuyOrder.getStock());
        assertEquals("buy", parsedBuyOrder.getType());
    }
}

