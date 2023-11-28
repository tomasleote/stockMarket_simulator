package nl.rug.aoop.trades.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.trades.TraderManager;

import java.util.Map;

/**
 * Class for the command that deals with updating the traders, implements Command interface.
 */
public class TraderUpdateCommand implements Command {

    private TraderManager traderManager;

    /**
     * Constructor of the class.
     * @param traderManager TraderManager class.
     */
    public TraderUpdateCommand(TraderManager traderManager) {
        this.traderManager = traderManager;
    }

    /**
     * Execute method of the command.
     * @param options Map that contains relevant trader to update.
     */
    @Override
    public void execute(Map<String, Object> options) {
        String traderUpdatedAsJson = (String) options.get("body");
        Trader traderUpdated = Trader.fromJson(traderUpdatedAsJson);
        traderManager.updateTraders(traderUpdated);
    }
}
