package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadSafeQueue {

    private ThreadSafeQueue queue;

    @BeforeEach
    public void setUp() {
        queue = new ThreadSafeQueue();
    }

    @Test
    public void testEnqueueAndDequeue() {
        Message message = new Message("Test Message", "body");
        queue.enqueue(message);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.getSize());
        Message dequeuedMessage = queue.dequeue();
        assertEquals(message, dequeuedMessage);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        Message message = new Message("Test Message","body");
        queue.enqueue(message);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, queue.getSize());
        Message message1 = new Message("Message 1", "body");
        Message message2 = new Message("Message 2", "body");
        queue.enqueue(message1);
        queue.enqueue(message2);
        assertEquals(2, queue.getSize());
    }

    @Test
    public void testClear() {
        Message message1 = new Message("Message 1", "body");
        Message message2 = new Message("Message 2", "body");
        queue.enqueue(message1);
        queue.enqueue(message2);
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());
    }
}
