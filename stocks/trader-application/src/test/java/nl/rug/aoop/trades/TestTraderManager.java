package nl.rug.aoop.trades;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;
import nl.rug.aoop.stocks.stock.application.StockManager;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class TestTraderManager {
    private ThreadSafeQueue messageQueue;
    private QueueMessageHandler messageHandler;
    private InetSocketAddress address;
    private int serverPort;
    private boolean serverStarted = false;
    private PrintWriter serverOut;

    private BufferedReader serverIn;
    private TraderRegistry mockTraderRegistry;

    private void startServer(){
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                serverPort  = s.getLocalPort();
                serverStarted = true;

                Socket serverSocket = s.accept();
                serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                log.info("Testing server started");

            }catch (IOException e){
                log.error("Testing server did not work", e);
            }
        }).start();

        await().atMost(2, TimeUnit.SECONDS).until(()->serverStarted);
    }

    private TraderManager startConnectedManager() throws IOException {
        startServer();
        InetSocketAddress address1 = new InetSocketAddress("localhost", serverPort);
        return new TraderManager(address1, messageQueue, messageHandler);
    }

    @Before
    public void setUp() throws IOException {
        // Initialize necessary objects before each test
        messageQueue = new ThreadSafeQueue();
        CommandHandler commandHandler = mock(CommandHandler.class);
        messageHandler = new QueueMessageHandler(commandHandler);
        mockTraderRegistry = mock(TraderRegistry.class);
    }

    @Test
    public void testLoadTraders() throws IOException {
        TraderManager traderManager = new TraderManager(address, messageQueue, messageHandler);

        // Ensure traderRegistry is not null after loading traders
        traderManager.loadTraders();
        assertNotNull(traderManager.getTraderRegistry());
    }

    @Test
    public void testStartBots() throws IOException {
        TraderManager onlineManager = startConnectedManager();

        onlineManager.loadTraders();
        onlineManager.startBots();
        assertFalse(onlineManager.getBots().isEmpty());
    }

    @Test
    public void testUpdateTraders() throws IOException {
        TraderManager onlineManager = startConnectedManager();
        onlineManager.loadTraders();
        onlineManager.startBots();

        // Create a mock trader
        Trader mockTrader = new Trader("mockId", "Mock Trader", 1000, new Portfolio(), 0);
        TraderRegistry traderRegistry = new TraderRegistry();
        traderRegistry.addTrader(mockTrader);
        onlineManager.setTraderRegistry(traderRegistry);

        // Update traders and ensure the trader is updated in the registry and bot
        onlineManager.updateTraders(mockTrader);
        Trader updatedTrader = onlineManager.getTraderRegistry().getTraderById("mockId");
        assertNotNull(updatedTrader);
        assertEquals("Mock Trader", updatedTrader.getName());
        assertEquals(1000, updatedTrader.getFunds(), 0.01);
        assertEquals("Mock Trader", onlineManager.getBots().get("mockId").getTrader().getName());
    }
}
