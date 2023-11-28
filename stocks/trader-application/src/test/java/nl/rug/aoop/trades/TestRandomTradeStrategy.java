package nl.rug.aoop.trades;

import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Stock;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.orders.LimitOrder;
import nl.rug.aoop.stocks.orders.Order;
import nl.rug.aoop.trades.commands.StocksUpdateCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestRandomTradeStrategy {

    private RandomTradeStrategy randomTradeStrategy;
    private TraderManager traderManager;
    private StockRegistry stockRegistry;

    Stock stock;
    @Before
    public void setUp() {
        traderManager = mock(TraderManager.class);
        randomTradeStrategy = new RandomTradeStrategy(traderManager);
        stockRegistry = new StockRegistry();
        stock = new Stock();
        stock.setInitialPrice(10.0);
        stock.setName("Stock");
        stock.setSymbol("AAPL");
        stock.setSharesOutstanding(300L);
        stockRegistry.addStocks("AAPL", stock);
    }


    @Test
    public void testTradeWhenTraderHasNoFundsAndNoStocks() {
        Trader trader = new Trader();
        trader.setFunds(0);
        trader.setStockPortfolio(new Portfolio());
        trader.setName("Name");
        trader.setId("id");
        trader.setClientId(0);

        Order order = randomTradeStrategy.trade(stockRegistry, trader);

        assertNull(order);
    }

    @Test
    public void testTradeWhenTraderHasFunds() {
        Trader trader = new Trader();
        trader.setFunds(1000);
        trader.setStockPortfolio(new Portfolio());
        trader.setName("Name");
        trader.setId("id");
        trader.setClientId(0);

        Order order = randomTradeStrategy.trade(stockRegistry, trader);

        assertNotNull(order);
    }

    @Test
    public void testTradeWhenTraderHasStocks() {
        Trader trader = new Trader();
        trader.setStockPortfolio(new Portfolio(Map.of("AAPL", 5000)));
        trader.setFunds(0);
        trader.setName("Name");
        trader.setId("id");
        trader.setClientId(0);

        Order order = randomTradeStrategy.trade(stockRegistry, trader);

        assertNotNull(order);
    }

    @Test
    public void testBuyStock() {
        Trader trader = new Trader();
        trader.setFunds(1000);
        trader.setStockPortfolio(new Portfolio());
        trader.setName("Name");
        trader.setId("id");
        trader.setClientId(0);
        randomTradeStrategy.trade(stockRegistry, trader); // needed because the stockRegistry is set in this method

        Order order = randomTradeStrategy.buyStock(trader);

        assertNotNull(order);
        assertEquals("buy", order.getType());
    }

    @Test
    public void testSellStock() {
        Trader trader = new Trader();
        trader.setFunds(0);
        trader.setStockPortfolio(new Portfolio(Map.of("AAPL", 5000)));
        trader.setName("Name");
        trader.setId("id");
        trader.setClientId(0);

        randomTradeStrategy.trade(stockRegistry, trader);

        Order order = randomTradeStrategy.sellStock(trader);

        assertNotNull(order);
        assertEquals("sell", order.getType());
    }
}

