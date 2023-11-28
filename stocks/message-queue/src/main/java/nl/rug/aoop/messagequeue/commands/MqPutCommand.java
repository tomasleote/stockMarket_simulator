package nl.rug.aoop.messagequeue.commands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

import java.util.Map;

/**
 * MqPutCommand class.
 */
@Slf4j
public class MqPutCommand implements Command {
    private final MessageQueue messageQueue;

    /**
     * Constructor for MqPutCommand class.
     * @param messageQueue the queue to enqueue messages to.
     */
    public MqPutCommand(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * execute Command extending from Command interface.
     * @param options a map, containing the options for the command.
     */
    @Override
    public void execute(Map<String, Object> options) {
        try {
            messageQueue.enqueue(new Message((String) options.get("body")));
        } catch (NullPointerException e) {
            log.error("Something went wrong.", e);
        }
    }
}
