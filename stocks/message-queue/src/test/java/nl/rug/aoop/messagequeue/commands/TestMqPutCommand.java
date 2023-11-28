package nl.rug.aoop.messagequeue.commands;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class TestMqPutCommand {
    MessageQueue testQueue = Mockito.mock(MessageQueue.class);
    MqPutCommand testCommand = new MqPutCommand(testQueue);

    @Test
    void testExecute() {
        Message testMessage = new Message("Testheader", "Testbody");
        testCommand.execute(Map.of("body", testMessage.toJson()));
        Mockito.verify(testQueue).enqueue(any(Message.class));
    }
}
