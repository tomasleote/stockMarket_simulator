package nl.rug.aoop.messagequeue.factory;

import nl.rug.aoop.command.*;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.messagequeue.commands.MqPutCommand;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * QueueCommandHandlerFactory class.
 */
public class QueueCommandHandlerFactory implements CommandHandlerFactory {

    /**
     * Message queue.
     */
    private MessageQueue queue;

    /**
     * Constructor.
     * @param queue to be passed.
     */
    public QueueCommandHandlerFactory(MessageQueue queue) {
        this.queue = queue;
    }


    /**
     * Method that creates a command handler with a given map of commands.
     * @return the CommandHandler.
     */
    @Override
    public CommandHandler createCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MqPutCommand(queue));

        return commandHandler;
    }
}
