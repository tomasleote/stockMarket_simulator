package nl.rug.aoop.trades;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.users.MQProducer;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Stock;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.orders.LimitOrder;
import nl.rug.aoop.stocks.orders.Order;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestTraderBot {

    private TraderBot traderBot;
    private Client mockClient;
    private Trader mockTrader;
    private TraderManager mockTraderManager;
    private MQProducer mockMQProducer;
    private TradeStrategy mockTradeStrategy;

    @Before
    public void setUp() throws Exception {
        mockClient = mock(Client.class);
        mockTrader = new Trader("mockId", "Mock Trader", 1000, new Portfolio(), 0);
        mockTraderManager = mock(TraderManager.class);
        mockMQProducer = mock(MQProducer.class);
        mockTradeStrategy = mock(TradeStrategy.class);

        traderBot = new TraderBot(mockClient, mockTrader, mockTraderManager);
        traderBot.setMqProducer(mockMQProducer);
        traderBot.setTradeStrategy(mockTradeStrategy);
    }

    @Test
    public void testSendOrder() {
        Order mockOrder = new LimitOrder(mockTrader, "type", 100.0, 10, "mockId");
        traderBot.setTraderManager(mockTraderManager);
        traderBot.setTrader(mockTrader);
        when(mockTradeStrategy.trade(mockTraderManager.getStockRegistry(), mockTrader)).thenReturn(mockOrder);

        traderBot.sendOrder();

        verify(mockMQProducer, times(1)).put(any(Message.class));
    }

    @Test
    public void testSendOrderWithNullOrder() {
        when(mockTradeStrategy.trade(any(StockRegistry.class), any(Trader.class))).thenReturn(null);

        traderBot.sendOrder();

        verify(mockMQProducer, never()).put(any(Message.class));
    }

    @Test
    public void testRequestUpdateTrader() {
        traderBot.requestUpdateTrader();

        verify(mockMQProducer, times(1)).put(any(Message.class));
    }

    @Test
    public void testRequestUpdateStocks() {
        traderBot.requestUpdateStocks();

        verify(mockMQProducer, times(1)).put(any(Message.class));
    }

    @Test
    public void testTerminate() {
        traderBot.terminate();

        assertFalse(traderBot.isRunning());
    }

}

