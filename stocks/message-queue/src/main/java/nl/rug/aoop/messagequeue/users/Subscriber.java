package nl.rug.aoop.messagequeue.users;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Subscriber of queues class.
 */
@Getter
@Setter
public class Subscriber implements MQConsumer {

    private MessageQueue messageQueue;

    /**
     * Constructor for the nl.rug.aoop.messagequeue.users.Subscriber.
     * @param messageQueue is the queue of messages.
     */
    public Subscriber(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Method to poll a message from the queue.
     * @return returns the message that has been polled.
     */
    @Override
    public Message poll() {
        return messageQueue.dequeue();
    }

}
