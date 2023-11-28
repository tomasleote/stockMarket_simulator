package nl.rug.aoop.command;

/**
 * CommandHandlerFactory interface.
 */
public interface CommandHandlerFactory {
    /**
     * Method that creates a CommandHandler given a commandMap.
     * @return the created commandHandler.
     */
    CommandHandler createCommandHandler();
}
