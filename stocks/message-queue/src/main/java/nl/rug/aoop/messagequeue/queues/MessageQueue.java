package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.Message;

/**
 * Class for parent message queue.
 */
public interface MessageQueue {
    /**
     * Method to enqueue the message.
     * @param message is the message to be enqueued
     */
    void enqueue(Message message);

    /**
     * Method to dequeue the message.
     * @return returns the message that has been dequeued
     */
    Message dequeue();

    /**
     * Method to get the size of the queue.
     * @return returns the size of the queue
     */
    int getSize();

}

