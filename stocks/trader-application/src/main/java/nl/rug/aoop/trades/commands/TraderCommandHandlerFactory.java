package nl.rug.aoop.trades.commands;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.trades.TraderManager;

/**
 * TraderCommandHandlerFactory class that implements CommandHandlerFactory.
 */
public class TraderCommandHandlerFactory implements CommandHandlerFactory {

    private TraderManager traderManager;

    /**
     * Constructor.
     * @param traderManager Stock manager of the application.
     */
    public TraderCommandHandlerFactory(TraderManager traderManager) {
        this.traderManager = traderManager;
    }


    /**
     * Method that creates a command handler with a given map of commands.
     * @return the CommandHandler.
     */
    @Override
    public CommandHandler createCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("StocksUpdate", new StocksUpdateCommand(traderManager));
        commandHandler.registerCommand("TraderUpdate", new TraderUpdateCommand(traderManager));
        return commandHandler;
    }
}