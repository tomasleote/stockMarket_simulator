package nl.rug.aoop.trades.commands;

import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.trades.TraderManager;
import java.util.Map;

/**
 * Class that handles the execution of updating the stocks list on the trader manager.
 */
@Slf4j
public class StocksUpdateCommand implements Command {

    private TraderManager traderManager;

    /**
     * Constructor.
     * @param traderManager Trader manager of the application.
     */
    public StocksUpdateCommand(TraderManager traderManager) {
        this.traderManager = traderManager;
    }

    /**
     * Execute method.
     * @param options Options map with the stocks list.
     * Converts stock list back from Json and sends it to the traderManager.
     */
    @Override
    public void execute(Map<String, Object> options) {
        try {
            String jsonString = (String) options.get("body");
            StockRegistry stockRegistry = StockRegistry.fromJson(jsonString);
            traderManager.setStockRegistry(stockRegistry);
        } catch (JsonSyntaxException e) {
            log.error("Error while converting Json StocksRegistry back to a stock registry: " + e.getMessage());
        }
    }
}
