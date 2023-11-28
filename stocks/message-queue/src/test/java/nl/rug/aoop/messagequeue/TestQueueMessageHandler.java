package nl.rug.aoop.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.networking.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
public class TestQueueMessageHandler {
    private CommandHandler testCommandHandler;
    private Message testMessage;
    private QueueMessageHandler testMessageHandler;

    @BeforeEach
    void setUp() {
        testCommandHandler = Mockito.mock(CommandHandler.class);
        testMessage =  new Message("TestHeader", "TestBody");
        testMessageHandler = new QueueMessageHandler(testCommandHandler);
    }

    @Test
    void testHandleMessage() {
        Client client = Mockito.mock(Client.class);
        String message = testMessage.toJson();
        Message message1 = new Message("MqPut", message);
        log.info("Message: " + message);
        testMessageHandler.handleMessage(message1.toJson());
        Mockito.verify(testCommandHandler).executeCommand(eq(message1.getHeader()), any());
    }

    @Test
    void testHandleMultipleMessages() {
        Client client = Mockito.mock(Client.class);
        Message message1 = new Message("MqPut1", testMessage.toJson());
        Message message2 = new Message("MqPut2", testMessage.toJson());
        Message message3 = new Message("MqPut3", testMessage.toJson());
        testMessageHandler.handleMessage(message1.toJson());
        testMessageHandler.handleMessage(message2.toJson());
        testMessageHandler.handleMessage(message3.toJson());
        Mockito.verify(testCommandHandler).executeCommand(eq(message1.getHeader()), any());
        Mockito.verify(testCommandHandler).executeCommand(eq(message2.getHeader()), any());
        Mockito.verify(testCommandHandler).executeCommand(eq(message3.getHeader()), any());
    }

        @Test
    void testHandleNullMessage() {
        Client client = Mockito.mock(Client.class);
        assertThrows(NullPointerException.class, () -> testMessageHandler.handleMessage(null));
    }
}
