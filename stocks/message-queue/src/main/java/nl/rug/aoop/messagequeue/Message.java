package nl.rug.aoop.messagequeue;

import lombok.Getter;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Message class.
 */
@Getter
@Slf4j
public class Message implements Comparable<Message> {
    private final String header;
    private final String body;
    private final LocalDateTime timestamp;
    private static Gson gson;

    /**
     * Constructor for the message.
     * @param header The header of the message.
     * @param body   The body of the message.
     * @throws IllegalArgumentException if either header or body is null.
     */
    public Message(String header, String body) {
        this.header = Objects.requireNonNull(header, "Message header cannot be null");
        this.body = Objects.requireNonNull(body, "Message body cannot be null");
        this.timestamp = LocalDateTime.now();
        gson = new GsonBuilder().registerTypeAdapter(Message.class, new GsonAdapter().nullSafe()).create();
    }

    /**
     * 2nd Constructor for the message.
     * @param header    The header of the message.
     * @param body      The body of the message.
     * @param timestamp The timestamp of the message.
     * @throws IllegalArgumentException if either header or body is null.
     */
    public Message(String header, String body, LocalDateTime timestamp) {
        this.header = Objects.requireNonNull(header, "Message header cannot be null");
        this.body = Objects.requireNonNull(body, "Message body cannot be null");
        this.timestamp = timestamp;
        gson = new GsonBuilder().registerTypeAdapter(Message.class, new GsonAdapter().nullSafe()).create();
    }

    /**
     * Constructor from a JSON string.
     * @param json the string.
     */
    public Message(String json) {
        try {
            Message message = fromJson(json);
            this.header = message.getHeader();
            this.body = message.getBody();
            this.timestamp = message.getTimestamp();
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }

    /**
     * Compares the timestamp of this message to the timestamp of another message.
     * @param other The other message.
     * @return The result of the comparison.
     */
    @Override
    public int compareTo(Message other) {
        return this.timestamp.compareTo(other.timestamp);
    }

    /**
     * Method to convert an object to Json representation.
     * @return the String as Json.
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * Method to convert a Json string to a Message object.
     * @param json the Json string.
     * @return the Message.
     */
    public static Message fromJson(String json) {
        log.info("Message from JSON: " + json);
        try {
            return gson.fromJson(json, Message.class);
        } catch (IllegalStateException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

}
