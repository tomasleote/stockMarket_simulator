package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.Message;

/**
 * Interface for consumers.
 */
public interface MQConsumer {

    /**
     * Interface initialisation of the poll method utilised by the subscriber class.
     * @return returns the message that has been polled.
     */
    Message poll();
}
