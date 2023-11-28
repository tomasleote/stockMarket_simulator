package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

/**
 * Test class for the OrderedQueue class.
 */
public class TestOrderedQueue {

    MessageQueue queue = null;

    /**
     * Set up method for the test class.
     */
    @BeforeEach
    void setUp() {
        queue = new OrderedQueue();
    }

    /**
     * Test method for the OrderedQueue class constructor.
     */
    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
    }

    /**
     * Test method for the OrderedQueue class that checks if the methods exist.
     * @throws NoSuchMethodException
     */
    @Test
    void testQueueMethods() throws NoSuchMethodException {
        assertNotNull(queue.getClass().getMethod("enqueue", Message.class));
        assertNotNull(queue.getClass().getMethod("dequeue"));
        assertNotNull(queue.getClass().getMethod("getSize"));
    }

    /**
     * Test method for the OrderedQueue class that checks if the messages are being enqueued.
     */
    @Test
    void testQueueEnqueue() throws InterruptedException {
        Message message1 = new Message("header", "body1");
        Thread.sleep(10);
        Message message2 = new Message("header", "body2");
        Thread.sleep(10);
        Message message3 = new Message("header", "body3");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());
    }

    /**
     * Test method for the OrderedQueue class that checks if the messages are ordered by timestamp.
     * @throws InterruptedException
     */
    @Test
    void testQueueEnqueueOrdering() throws InterruptedException {
        Message message1 = new Message("header", "body");
        Thread.sleep(10);
        Message message2 = new Message("header", "body");
        Thread.sleep(10);
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
        assertEquals(message3, queue.dequeue());
    }

    /**
     * Test method for the OrderedQueue class that checks if it throws an error in case a null message is enqueued.
     */
    @Test
    void testEnqueueNullMessage() {
        assertThrows(NullPointerException.class, () -> queue.enqueue(null));
    }

    /**
     * Test method for the OrderedQueue class that checks if a message with an empty body is not added to the queue.
     */
    @Test
    void testEnqueueEmptyBodyMessage() {
        Message message = new Message("header", "");
        queue.enqueue(message);
        assertEquals(0, queue.getSize());  // No message should be added.
    }

    /**
     * Test method for the OrderedQueue class that checks if an exception is thrown when trying to dequeue an empty queue.
     */
    @Test
    void testDequeueFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Test method for the OrderedQueue class that checks if the method works as intended.
     */
    @Test
    void testDequeueBehavior() {
        Message message1 = new Message("header", "body");
        queue.enqueue(message1);
        assertEquals(1, queue.getSize());

        Message dequeuedMessage = queue.dequeue();
        assertEquals(0, queue.getSize());
        assertEquals(message1, dequeuedMessage);
    }

    /**
     * Test method for the OrderedQueue class that checks if the method gets the right size.
     */
    @Test
    void testGetSize() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());
    }
}
