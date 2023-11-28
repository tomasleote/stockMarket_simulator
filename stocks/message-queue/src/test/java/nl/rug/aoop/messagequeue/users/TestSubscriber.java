package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.OrderedQueue;
import nl.rug.aoop.messagequeue.queues.UnorderedQueue;
import nl.rug.aoop.messagequeue.users.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Subscriber class.
 */
public class TestSubscriber {

    private MessageQueue orderedQueue;
    private MessageQueue unorderedQueue;
    private Subscriber orderedSubscriber;
    private Subscriber unorderedSubscriber;

    /**
     * Set up method for the test class.
     */
    @BeforeEach
    void setUp() {
        orderedQueue = new OrderedQueue();
        unorderedQueue = new UnorderedQueue();
        orderedSubscriber = new Subscriber(orderedQueue);
        unorderedSubscriber = new Subscriber(unorderedQueue);
    }

    /**
     * Test method for the Subscriber class constructor.
     */
    @Test
    void testConstructor() {
        assertNotNull(orderedSubscriber.getMessageQueue());
        assertNotNull(unorderedSubscriber.getMessageQueue());
    }

    /**
     * Test method for the Subscriber class that checks if the subscriber receives the message and if an exception is thrown
     * if trying to poll from an empty ordered queue.
     */
    @Test
    void testPollOrdered() {
        Message message1 = new Message("header1", "body1");
        orderedQueue.enqueue(message1);
        assertEquals(message1, orderedSubscriber.poll());
        assertThrows(NoSuchElementException.class, () -> orderedSubscriber.poll());
    }

    /**
     * Test method for the Subscriber class that checks if the subscriber receives the message and if an exception is thrown
     * if trying to poll from an empty ordered queue.
     */
    @Test
    void testPollUnordered() {
        Message message2 = new Message("header2", "body2");
        unorderedQueue.enqueue(message2);
        assertEquals(message2, unorderedSubscriber.poll());
        assertThrows(NoSuchElementException.class, () -> unorderedSubscriber.poll());
    }

    /**
     * Test method for the Subscriber class that checks if the messages in the ordered queue are being polled in order.
     */
    @Test
    void testMultipleMessagesPoll() {
        Message message1 = new Message("header1", "body1");
        Message message2 = new Message("header2", "body2");
        orderedQueue.enqueue(message1);
        orderedQueue.enqueue(message2);
        assertEquals(message1, orderedSubscriber.poll());
        assertEquals(message2, orderedSubscriber.poll());
    }

    /**
     * Test method for the Subscriber class that checks if both types of queues throw an exception in case
     * of polling from empty queues.
     */
    @Test
    void testPollFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> orderedSubscriber.poll());
        assertThrows(NoSuchElementException.class, () -> unorderedSubscriber.poll());
    }

}
