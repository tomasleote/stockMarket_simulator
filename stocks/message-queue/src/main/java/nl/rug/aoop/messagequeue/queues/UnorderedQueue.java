package nl.rug.aoop.messagequeue.queues;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.Message;

import java.util.*;

/**
 * Unordered Queue class.
 */
@Getter
@Setter
public class UnorderedQueue implements MessageQueue {

    private LinkedList<Message> queue;

    /**
     * Constructor of the unordered queue class.
     */
    public UnorderedQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Method to enqueue messages.
     * @param message is the message to be queued.
     * @throws NullPointerException if the provided message is null.
     */
    public void enqueue(Message message) {
        Objects.requireNonNull(message, "Message body cannot be null");
        queue.add(message);
    }

    /**
     * Method used to dequeue a message from the queue.
     * @return returns the message that has been dequeued.
     * @throws NoSuchElementException if the queue is empty.
     */
    public Message dequeue() {
        if(queue.isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot dequeue.");
        }
        return queue.remove();
    }

    /**
     * Method used to get the size of the queue.
     * @return returns the size of the queue.
     */
    public int getSize() {
        return queue.size();
    }

}

