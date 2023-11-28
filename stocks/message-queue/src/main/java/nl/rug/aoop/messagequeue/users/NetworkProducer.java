package nl.rug.aoop.messagequeue.users;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.networking.client.Client;

/**
 * Class for network producer.
 */
@Slf4j
public class NetworkProducer implements MQProducer {

    private final Client client;

    /**
     * Constructor.
     * @param client the client.
     */
    public NetworkProducer(Client client) {
        this.client = client;
    }

    /**
     * Put method. Sends message to server through client.
     * @param message message to be enqueued.
     */
    public void put(Message message) {
        try {
            client.sendMessage(message.toJson());
        } catch (NullPointerException e) {
            log.error("Unable to send message");
        }
    }
}
