package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.messagequeue.commands.MqPutCommand;
import nl.rug.aoop.stocks.orders.OrderHandler;
import nl.rug.aoop.stocks.stock.application.StockManager;

/**
 * StockCommandHandlerFactory class that implements CommandHandlerFactory.
 */
public class StockCommandHandlerFactory implements CommandHandlerFactory {

    private StockManager stockManager;
    private OrderHandler orderHandler;

    /**
     * Constructor.
     * @param stockManager Stock manager of the application.
     * @param orderHandler Order handler of the application.
     */
    public StockCommandHandlerFactory(StockManager stockManager, OrderHandler orderHandler) {
        this.stockManager = stockManager;
        this.orderHandler = orderHandler;
    }


    /**
     * Method that creates a command handler with a given map of commands.
     * @return the CommandHandler.
     */
    @Override
    public CommandHandler createCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("buy", new BuyOrderCommand(orderHandler));
        commandHandler.registerCommand("sell", new SellOrderCommand(orderHandler));
        commandHandler.registerCommand("buyMarket", new BuyOrderCommand(orderHandler));
        commandHandler.registerCommand("sellMarket", new SellOrderCommand(orderHandler));
        commandHandler.registerCommand("UpdateStocks", new UpdateStocksCommand(stockManager));
        commandHandler.registerCommand("UpdateTrader", new UpdateTraderCommand(stockManager));
        commandHandler.registerCommand("MqPut", new MqPutCommand(stockManager.getQueue()));
        return commandHandler;
    }
}
