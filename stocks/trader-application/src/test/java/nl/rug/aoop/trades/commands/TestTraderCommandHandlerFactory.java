package nl.rug.aoop.trades.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.trades.TraderManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TestTraderCommandHandlerFactory {

    private TraderManager traderManager;
    private TraderCommandHandlerFactory commandHandlerFactory;

    @Before
    public void setUp() throws IOException {
        InetSocketAddress inetSocketAddress = mock(InetSocketAddress.class);
        ThreadSafeQueue threadSafeQueue = mock(ThreadSafeQueue.class);
        QueueMessageHandler queueMessageHandler = mock(QueueMessageHandler.class);
        traderManager = new TraderManager(inetSocketAddress, threadSafeQueue, queueMessageHandler);
        commandHandlerFactory = new TraderCommandHandlerFactory(traderManager);
    }

    @Test
    public void testCreateCommandHandler() {
        CommandHandler commandHandler = commandHandlerFactory.createCommandHandler();
        Command stocksUpdateCommand = new StocksUpdateCommand(traderManager);
        Command traderUpdateCommand = new TraderUpdateCommand(traderManager);
        Map<String, Command> expectedCommandMap = new HashMap<>();
        expectedCommandMap.put("StocksUpdate", stocksUpdateCommand);
        expectedCommandMap.put("TraderUpdate", traderUpdateCommand);

        assertTrue(commandHandler.getCommandMap().containsKey("StocksUpdate"));
        assertTrue(commandHandler.getCommandMap().containsKey("TraderUpdate"));

        assertTrue(commandHandler.getCommandMap().get("StocksUpdate") instanceof StocksUpdateCommand);
        assertTrue(commandHandler.getCommandMap().get("TraderUpdate") instanceof TraderUpdateCommand);
    }
}

