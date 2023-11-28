package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.OrderedQueue;
import nl.rug.aoop.messagequeue.queues.UnorderedQueue;
import nl.rug.aoop.messagequeue.users.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Publisher class.
 */
public class TestPublisher {

    private MessageQueue orderedQueue;
    private MessageQueue unorderedQueue;
    private Publisher orderedPublisher;
    private Publisher unorderedPublisher;

    /**
     * Set up method for the test class.
     */
    @BeforeEach
    void setUp() {
        orderedQueue = new OrderedQueue();
        unorderedQueue = new UnorderedQueue();
        orderedPublisher = new Publisher(orderedQueue);
        unorderedPublisher = new Publisher(unorderedQueue);
    }

    /**
     * Test method for the Publisher class constructor.
     */
    @Test
    void testPublisherConstructor() {
        assertNotNull(orderedPublisher);
        assertNotNull(unorderedPublisher);
    }

    /**
     * Test method for the Publisher class for putting a message in ordered queue.
     */
    @Test
    void testPublishToOrderedQueue() {
        Message message = new Message("header", "body");
        orderedPublisher.put(message);
        assertEquals(1, orderedQueue.getSize());
        assertEquals(message, orderedQueue.dequeue());
    }

    /**
     * Test method for the Publisher class for putting a message in unordered queue.
     */
    @Test
    void testPublishToUnorderedQueue() {
        Message message = new Message("header", "body");
        unorderedPublisher.put(message);
        assertEquals(1, unorderedQueue.getSize());
        assertEquals(message, unorderedQueue.dequeue());
    }

    /**
     * Test method for the Publisher class for putting a null message.
     */
    @Test
    void testNullMessagePublication() {
        assertThrows(IllegalArgumentException.class, () -> orderedPublisher.put(null));
        assertThrows(IllegalArgumentException.class, () -> unorderedPublisher.put(null));
    }

    /**
     * Test method for the Publisher class for putting a message with null body.
     */
    @Test
    void testMessageWithEmptyBodyPublication() {
        Message message = new Message("header", "");
        assertThrows(IllegalArgumentException.class, () -> orderedPublisher.put(message));
        assertThrows(IllegalArgumentException.class, () -> unorderedPublisher.put(message));
    }

}
