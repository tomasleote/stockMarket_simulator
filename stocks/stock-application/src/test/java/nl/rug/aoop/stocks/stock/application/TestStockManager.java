package nl.rug.aoop.stocks.stock.application;

import static org.mockito.Mockito.*;

import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.model.StockExchange;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;
import nl.rug.aoop.stocks.orders.OrderHandler;

import nl.rug.aoop.util.YamlLoader;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;

public class TestStockManager {
    private StockManager stockManager;
    private OrderHandler mockOrderHandler;
    private StockExchange mockStockExchange;
    private MessageQueue mockMessageQueue;
    private Server mockServer;
    private TraderUpdater mockTraderUpdater;
    private YamlLoader loader;
    private StockRegistry mockStockRegistry;
    private TraderRegistry mockTraderRegistry;
    private ClientHandler mockClientHandler;
    private Trader mockTrader;

    @Before
    public void setUp() {
        mockOrderHandler = mock(OrderHandler.class);
        mockStockExchange = mock(StockExchange.class);
        mockMessageQueue = mock(MessageQueue.class);
        mockServer = mock(Server.class);
        mockTraderUpdater = mock(TraderUpdater.class);
        loader = mock(YamlLoader.class);
        mockStockRegistry = mock(StockRegistry.class);
        mockTraderRegistry = mock(TraderRegistry.class);
        mockClientHandler = mock(ClientHandler.class);
        mockTrader = mock(Trader.class);

        stockManager = new StockManager(mockOrderHandler, mockStockExchange, mockMessageQueue, mockServer, mockTraderUpdater);
    }

    @Test
    public void testLoadStocks() throws IOException {
        when(loader.load(eq(StockRegistry.class))).thenReturn(mockStockRegistry);
        stockManager.loadStocks();
        verify(mockStockExchange).setStockRegistry(any(StockRegistry.class));
    }

    @Test
    public void testLoadTraders() throws IOException {
        when(loader.load(eq(TraderRegistry.class))).thenReturn(mockTraderRegistry);
        stockManager.loadTraders();
        verify(mockStockExchange).setTraderRegistry((any(TraderRegistry.class)));
    }

    @Test
    public void testSendStocks() {
        when(mockServer.getClientHandlers()).thenReturn((List.of(mockClientHandler)));
        when(mockStockExchange.getStockRegistry()).thenReturn(mockStockRegistry);
        when(mockStockRegistry.toJson()).thenReturn("stock registry as json placeholder");
        stockManager.sendStocks();
        verify(mockServer.getClientHandlers().get(0), times(1)).sendMessage(anyString());
    }

    @Test
    public void testSendTrader() {
        when(mockServer.getClientHandlers()).thenReturn(List.of(mockClientHandler));
        when(mockStockExchange.getTraderRegistry()).thenReturn(mockTraderRegistry);
        when(mockTraderRegistry.getTraderById("Trader1")).thenReturn(mockTrader);
        when(mockTrader.toJson()).thenReturn("Trader as json placeholder");
        stockManager.sendTrader("Trader1");
        verify(mockServer.getClientHandlers().get(0), times(1)).sendMessage(anyString());
    }
}
