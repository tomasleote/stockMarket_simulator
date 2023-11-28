package nl.rug.aoop.messagequeue.queues;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.Message;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class for child ordered queue.
 */
@Getter
@Setter
public class OrderedQueue implements MessageQueue {

    private SortedMap<LocalDateTime, List<Message>> queue;

    /**
     * Constructor of the class.
     */
    public OrderedQueue() {
        queue = new TreeMap<>();
    }

    /**
     * Method to enqueue the message.
     * @param message is the message to be enqueued.
     */
    @Override
    public void enqueue(Message message) {
        Objects.requireNonNull(message, "Message body cannot be null");
        if (!message.getBody().equals("")) {
            LocalDateTime timeStamp = message.getTimestamp();

            // Check if the timestamp key already exists in the map.
            if (queue.containsKey(timeStamp)) {
                // If it does, add the message to the existing list.
                queue.get(timeStamp).add(message);
            } else {
                // If it doesn't, create a new list, add the message, and put it in the map.
                List<Message> messagesForTimestamp = new ArrayList<>();
                messagesForTimestamp.add(message);
                queue.put(timeStamp, messagesForTimestamp);
            }
        } else {
            System.out.println("Message object cannot be null");
        }
    }

    /**
     * Method to dequeue the message.
     * @return returns the message that has been dequeued.
     */
    @Override
    public Message dequeue() {
        if(queue.isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot dequeue.");
        }

        LocalDateTime head = queue.firstKey();
        List<Message> messages = queue.get(head);

        if (messages.size() == 1) {
            // If only one message for this timestamp, remove the entire entry
            return queue.remove(head).get(0);
        } else {
            // If multiple messages for this timestamp, remove the first from the list and return
            return messages.remove(0);
        }
    }

    /**
     * Method to get the size of the queue.
     * @return returns the size of the queue.
     */
    @Override
    public int getSize() {
        // To get the total size, we'll sum the sizes of all lists
        return queue.values().stream().mapToInt(List::size).sum();
    }

}

