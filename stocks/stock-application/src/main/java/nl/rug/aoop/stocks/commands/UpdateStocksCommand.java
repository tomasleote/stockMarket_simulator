package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.stock.application.StockManager;

import java.util.Map;

/**
 * UpdateStocksCommand class that implements Command interface.
 */
public class UpdateStocksCommand implements Command {

    private final StockManager stockManager;

    /**
     * Constructor.
     * @param stockManager Stock manager of the application.
     */
    public UpdateStocksCommand(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    /**
     * Execute method of the command. Simply calls to the stocksManager to update the stocks.
     * @param options empty map.
     */
    @Override
    public void execute(Map<String, Object> options) {
        stockManager.sendStocks();
    }
}
