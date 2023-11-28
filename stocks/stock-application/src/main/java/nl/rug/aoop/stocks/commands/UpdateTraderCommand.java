package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.stock.application.StockManager;

import java.util.Map;

/**
 * UpdateTraderCommand class that implements Command interface.
 */
public class UpdateTraderCommand implements Command {
    private StockManager stockManager;

    /**
     * Constructor.
     * @param stockManager Stock manager of the application.
     */
    public UpdateTraderCommand(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    /**
     * Execute method of the command. Simply calls to the stocksManager to update the stocks.
     * @param options empty map.
     */
    @Override
    public void execute(Map<String, Object> options) {
        String traderId = (String) options.get("body");
        stockManager.sendTrader(traderId);
    }
}
