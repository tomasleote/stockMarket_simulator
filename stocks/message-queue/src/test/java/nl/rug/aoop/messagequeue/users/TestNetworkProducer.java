package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.networking.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestNetworkProducer {

    private NetworkProducer networkProducer;
    private Client client;
    private Message message;

    @BeforeEach
    void setUp() {
        client = Mockito.mock(Client.class);
        message = Mockito.mock(Message.class);
        networkProducer = new NetworkProducer(client);
    }

    @Test
    void testPutWithNullMessage() {
        when(message.toJson()).thenThrow(new NullPointerException());

        networkProducer.put(message);

        verify(client, times(0)).sendMessage(anyString());
    }

    @Test
    void testPutWhenSendMessageThrowsException() {
        String jsonMessage = "{\"messageType\":\"MqPut\",\"body\":\"valid message\"}";
        when(message.toJson()).thenReturn(jsonMessage);
        doThrow(new RuntimeException("Failed to send message")).when(client).sendMessage(anyString());
        assertThrows(RuntimeException.class, () -> networkProducer.put(message));
    }
}
