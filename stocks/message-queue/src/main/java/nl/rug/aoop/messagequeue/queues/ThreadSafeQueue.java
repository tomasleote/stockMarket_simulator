package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.Message;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * ThreadSafeQueue class. This class implements the MessageQueue interface and provides a thread-safe queue.
 */
public class ThreadSafeQueue implements MessageQueue {

    private final PriorityBlockingQueue<Message> queue;

    /**
     * Constructor.
     */
    public ThreadSafeQueue() {
        this.queue = new PriorityBlockingQueue<>();
    }

    /**
     * Enqueues a message into the queue. The message will be ordered based on its timestamp.
     * @param message The message to be enqueued.
     */
    @Override
    public void enqueue(Message message) {
        queue.put(message);
    }

    /**
     * Dequeues a message from the queue. If the queue is empty, this method will block
     * until a message becomes available.
     *
     * @return The dequeued message.
     */
    @Override
    public Message dequeue() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Dequeue operation was interrupted", e);
        }
    }

    /**
     * Checks if the queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the current size of the queue.
     * @return The size of the queue.
     */
    public int getSize() {
        return queue.size();
    }

    /**
     * Clears all messages from the queue.
     */
    public void clear() {
        queue.clear();
    }
}
