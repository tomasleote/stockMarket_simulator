package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.UnorderedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UnorderedQueue class.
 */
public class TestUnorderedQueue {

    UnorderedQueue queue = null;

    /**
     * Set up method for the test class.
     */
    @BeforeEach
    void setUp() {
        queue = new UnorderedQueue();
    }

    /**
     * Test method for the UnorderedQueue class constructor.
     */
    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
        assertEquals(0, queue.getSize());
    }

    /**
     * Test method for the UnorderedQueue class that checks if the methods exist.
     * @throws NoSuchMethodException
     */
    @Test
    void testQueueMethods() throws NoSuchMethodException {
        assertNotNull(queue.getClass().getMethod("enqueue", Message.class));
        assertNotNull(queue.getClass().getMethod("dequeue"));
        assertNotNull(queue.getClass().getMethod("getSize"));
    }

    /**
     * Test method for the UnorderedQueue class that checks if an exception is thrown in case a null is being enqueued.
     */
    @Test
    void testEnqueueNullMessage() {
        assertThrows(NullPointerException.class, () -> queue.enqueue(null));
    }

    /**
     * Test method for the UnorderedQueue class that tests if an exception is thrown if trying to dequeue from an empty queue.
     */
    @Test
    void testDequeue() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Test method for the UnorderedQueue class that tests if exceptions are thrown when trying to dequeue from an empty queue.
     */
    @Test
    void testMultipleDequeueOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    /**
     * Test method for the UnorderedQueue class that checks if the exception message for trying to enqueue a null value is correct.
     */
    @Test
    void testEnqueueNullExceptionMessage() {
        Exception exception = assertThrows(NullPointerException.class, () -> queue.enqueue(null));
        assertEquals("Message body cannot be null", exception.getMessage());
    }

    /**
     * Test method for the UnorderedQueue class that checks if the size is correct after multiple queue/dequeues.
     */
    @Test
    void testGetSize() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        assertEquals(0, queue.getSize());

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());

        Message message = queue.dequeue();
        assertEquals(2, queue.getSize());
        message = queue.dequeue();
        assertEquals(1, queue.getSize());
        message = queue.dequeue();
        assertEquals(0, queue.getSize());
    }

    /**
     * Test method for the UnorderedQueue class that checks if the order of the enqueued and dequeued messages is first in first out.
     */
    @Test
    void testQueueOrdering() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message3, queue.dequeue());
        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
    }

    /**
     * Test method for the UnorderedQueue class that checks if a message with an empty body is added to the queue.
     */
    @Test
    void testEnqueueEmptyBody() {
        Message emptyMessage = new Message("header", "");
        queue.enqueue(emptyMessage);
        assertEquals(1, queue.getSize());
    }

    /**
     * Test method for the UnorderedQueue class that checks if a duped message gets dequeued properly.
     */
    @Test
    void testDuplicateMessages() {
        Message message = new Message("header", "body");
        queue.enqueue(message);
        queue.enqueue(message);

        assertEquals(2, queue.getSize());
        assertNotNull(queue.dequeue());
        assertNotNull(queue.dequeue());
        assertEquals(0, queue.getSize());
    }


}
