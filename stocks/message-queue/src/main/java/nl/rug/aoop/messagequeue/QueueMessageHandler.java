package nl.rug.aoop.messagequeue;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.networking.MessageHandler;

import java.util.Map;

/**
 * Class for message handler.
 */
@Slf4j
@Getter
@Setter
public class QueueMessageHandler implements MessageHandler {

    private CommandHandler commandHandler;

    /**
     * Constructor for this class.
     * @param commandHandler Handler for commands.
     */
    public QueueMessageHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * Handle message method.
     * @param message the message.
     */
    @Override
    public void handleMessage(String message) {
        Message messageToHandle = new Message(message);
        String command = messageToHandle.getHeader();
        Map options = Map.of("header", messageToHandle.getHeader(),
                "body", messageToHandle.getBody());
        commandHandler.executeCommand(command, options);
    }

}
