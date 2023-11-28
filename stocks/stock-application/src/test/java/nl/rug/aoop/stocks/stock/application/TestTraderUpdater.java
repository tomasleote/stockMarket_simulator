package nl.rug.aoop.stocks.stock.application;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.model.StockExchange;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestTraderUpdater {

    private Server mockServer;
    private StockExchange mockStockExchange;
    private TraderRegistry mockTraderRegistry;
    private StockRegistry mockStockRegistry;

    @Before
    public void setUp() {
        mockServer = mock(Server.class);
        mockStockExchange = mock(StockExchange.class);
        mockTraderRegistry = mock(TraderRegistry.class);
        mockStockRegistry = mock(StockRegistry.class);
    }

    @Test
    public void testInitialization() {
        TraderUpdater updater = new TraderUpdater(mockServer, mockStockExchange, mockTraderRegistry);

        assert updater.getServer() == mockServer;
        assert updater.getStockExchange() == mockStockExchange;
        assert updater.getTraders() == mockTraderRegistry;
        assert updater.getIsRunning().equals(false);
    }

    @Test
    public void testRunMethodSendsMessagesToClients() throws InterruptedException {
        // Mocking objects
        Trader trader1 = mock(Trader.class);
        Trader trader2 = mock(Trader.class);
        ClientHandler mockClientHandler1 = mock(ClientHandler.class);
        ClientHandler mockClientHandler2 = mock(ClientHandler.class);
        Map <String, Trader> traderMap = new HashMap<>();
        traderMap.put("Trader1", trader1);
        traderMap.put("Trader2", trader2);

        // Setting up mock behavior
        when(mockServer.getClientHandlers()).thenReturn(List.of(mockClientHandler1, mockClientHandler2));
        when(mockTraderRegistry.getTraders()).thenReturn(traderMap);
        when(trader1.toMessage()).thenReturn(new Message("Trader1", "Trader1Data"));
        when(trader2.toMessage()).thenReturn(new Message("Trader2", "Trader2Data"));
        when(mockStockExchange.getStockRegistry()).thenReturn(mockStockRegistry);
        when(mockStockExchange.getStockRegistry().toMessage()).thenReturn(new Message("StockRegistry", "StockData"));

        // Creating the updater
        TraderUpdater updater = new TraderUpdater(mockServer, mockStockExchange, mockTraderRegistry);

        // Running the updater (in a separate thread, if needed)
        Thread updaterThread = new Thread(updater);
        updaterThread.start();

        // Waiting for a short time to allow the updater to send messages
        Thread.sleep(1000);

        // Verifying that the messages were sent to the clients
        verify(mockClientHandler1, times(1)).sendMessage(any(String.class));
        verify(mockClientHandler2, times(1)).sendMessage(any(String.class));

        // Terminating the updater
        updater.terminate();

        // Waiting for the updater thread to finish (if needed)
        updaterThread.join();
    }

    @Test
    public void testTerminateMethod() {
        TraderUpdater updater = new TraderUpdater(mockServer, mockStockExchange, mockTraderRegistry);

        // Verifying the initial state
        assert updater.getIsRunning().equals(false);

        // Terminating the updater
        updater.terminate();

        // Verifying that the isRunning flag is set to false after termination
        assert updater.getIsRunning().equals(false);
    }
}
