package nl.rug.aoop.messagequeue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Message class.
 */
public class TestMessage {

    private Message message;
    private String messageHeader;
    private String messageBody;

    /**
     * Set up method for the test class.
     */
    @BeforeEach
    void setUp() {
        messageHeader = "header";
        messageBody = "body";
        message = new Message(messageHeader, messageBody);
    }

    /**
     * Test method for the Message class constructor.
     */
    @Test
    void testMessageConstructor() {
        assertEquals(messageHeader, message.getHeader());
        assertEquals(messageBody, message.getBody());
        assertNotNull(message.getTimestamp());
    }

    /**
     * Test method for the Message class.
     */
    @Test
    void testMessageImmutable() {
        List<Field> fields = List.of(Message.class.getDeclaredFields());
        for (Field field : fields) {
            if (field.getName().equals("gson")) {
                continue;
            } else {
                assertTrue(Modifier.isFinal(field.getModifiers()), field.getName() + " is not final");
            }
        }
    }

    /**
     * Test method for the Message class.
     */
    @Test
    void testNullHeaderConstructor() {
        assertThrows(NullPointerException.class, () -> new Message(null, "bodi"));
    }

    /**
     * Test method for the Message class.
     */
    @Test
    void testNullBodyConstructor() {
        assertThrows(NullPointerException.class, () -> new Message("messageHeader", null));
    }
}
