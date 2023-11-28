package nl.rug.aoop.stocks.orders;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Trader;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.sound.sampled.Port;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TestLimitOrder {

    private Portfolio portfolio;
    Trader trader;
    @BeforeEach
    public void setUp() {
        portfolio = mock(Portfolio.class);
        trader = new Trader("id", "name", 120.0, portfolio, 0);
    }
    @Test
    public void testGetters() {
        LimitOrder limitOrder = new LimitOrder(trader, "BUY", 100.0, 10, "XYZ");

        assertEquals("BUY", limitOrder.getType());
        assertEquals(100.0, limitOrder.getPrice(), 0.01);
        assertEquals(10, limitOrder.getNoOfShares());
        assertEquals("XYZ", limitOrder.getStock());
        assertEquals(trader, limitOrder.getTrader());
    }

    @Test
    public void testToJson() {
        LimitOrder limitOrder = new LimitOrder(trader, "BUY", 100.0, 10, "XYZ");
        String expectedJson = "{\"type\":\"BUY\",\"price\":100.0,\"noOfShares\":10,\"stock\":\"XYZ\"}";

        assertEquals(expectedJson, limitOrder.toJson());
    }

    @Test
    public void testFromJson() {
        String json = "{\"traderId\":\"id\",\"type\":\"BUY\",\"price\":100.0,\"noOfShares\":10,\"stock\":\"XYZ\"}";
        LimitOrder limitOrder = LimitOrder.fromJson(json);

        assertEquals("BUY", limitOrder.getType());
        assertEquals(100.0, limitOrder.getPrice(), 0.01);
        assertEquals(10, limitOrder.getNoOfShares());
        assertEquals("XYZ", limitOrder.getStock());
    }

    @Test
    public void testSetNoOfShares() {
        LimitOrder limitOrder = new LimitOrder(trader, "BUY", 100.0, 10, "XYZ");

        limitOrder.setNoOfShares(20);
        assertEquals(20, limitOrder.getNoOfShares());
    }
}
