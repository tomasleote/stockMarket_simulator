package nl.rug.aoop.messagequeue.users;

import lombok.Setter;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Publishers class.
 */
@Setter
public class Publisher implements MQProducer {

    private MessageQueue messageQueue ;

    /**
     * Constructor of the class.
     * @param messageQueue is the queue of messages.
     */
    public Publisher(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Method to put a message into the queue.
     * @param message is the message to be put into the queue.
     */
    @Override
    public void put(Message message) {
        if(message == null  || message.getBody().equals("")) {
            throw new IllegalArgumentException("Cannot put a null message into the queue.");
        } else {
            messageQueue.enqueue(message);
        }
    }
}