package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.Message;

/**
 * Interface for producers.
 */
public interface MQProducer {

    /**
     * Interface initialisation of the put method utilised by the publisher class.
     * @param message the message to put in the queue
     */
    void put(Message message);
}
