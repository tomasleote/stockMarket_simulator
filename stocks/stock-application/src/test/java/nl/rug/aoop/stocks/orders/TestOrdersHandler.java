package nl.rug.aoop.stocks.orders;

import nl.rug.aoop.stocks.model.*;
import nl.rug.aoop.stocks.stock.application.StockManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestOrdersHandler {
    private OrdersHandler orderHandler;
    private StockExchange stockExchange;
    private StockRegistry stockRegistry;
    private TraderRegistry traderRegistry;
    private Trader trader;
    private Trader trader2;
    private StockManager stockManager;

    @Before
    public void setUp() {
        trader = new Trader("id", "name", 1000.0, new Portfolio(), 0);
        trader2 = new Trader("id2", "name2", 1000.0, new Portfolio(), 1);
        stockRegistry = new StockRegistry();
        traderRegistry = new TraderRegistry();
        traderRegistry.addTrader(trader);
        traderRegistry.addTrader(trader2);
        stockManager = mock(StockManager.class);
        stockManager.loadStocks();
        stockExchange = new StockExchange(traderRegistry, stockRegistry);
        orderHandler = new OrdersHandler(stockExchange);
    }

    @Test
    public void testHandleBuyOrder() {
        BuyOrder buyOrder = new BuyOrder(trader, 50.0, 5, "stock");
        orderHandler.handleOrder(buyOrder);

        assertTrue(orderHandler.getBids().contains(buyOrder));
    }

    @Test
    public void testHandleSellOrder() {
        SellOrder sellOrder = new SellOrder(trader, 50.0, 5, "stock");
        orderHandler.handleOrder(sellOrder);

        assertTrue(orderHandler.getAsks().contains(sellOrder));
    }

    @Test
    public void testMatchingBuyOrder() {
        BuyOrder buyOrder = new BuyOrder(trader, 300.0, 5, "NVDA");

        trader2.buy("NVDA", 5);
        SellOrder sellOrder = new SellOrder(trader2, 299.0, 5, "NVDA");
        orderHandler.handleOrder(sellOrder);
        orderHandler.handleOrder(buyOrder);

        assertTrue(orderHandler.getBids().isEmpty());
        assertTrue(orderHandler.getAsks().isEmpty());
    }
}
