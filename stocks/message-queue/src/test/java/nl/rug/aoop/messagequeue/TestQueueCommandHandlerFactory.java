package nl.rug.aoop.messagequeue;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.commands.MqPutCommand;
import nl.rug.aoop.messagequeue.factory.QueueCommandHandlerFactory;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TestQueueCommandHandlerFactory {
    private CommandHandler testCommandHandler;

    @BeforeEach
    void setUp() {
        MessageQueue testMessageQueue = mock(MessageQueue.class);
        QueueCommandHandlerFactory factory = new QueueCommandHandlerFactory(testMessageQueue);
        testCommandHandler = factory.createCommandHandler();
    }

    @Test
    void testCreateCommandHandler() {
        assertTrue(testCommandHandler.getCommandMap().get("MqPut") instanceof MqPutCommand);
    }
}
