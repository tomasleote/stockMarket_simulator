package nl.rug.aoop.stocks.model;

import lombok.Getter;
import java.util.*;

/**
 * Class for trader registry.
 */
@Getter
public class TraderRegistry {
    private Map<String, Trader> traders;

    /**
     * Constructor of the class.
     */
    public TraderRegistry() {
        traders = new LinkedHashMap<>();
    }

    /**
     * Adds a trader.
     * @param trader trader to add.
     */
    public void addTrader(Trader trader) {
        traders.put(trader.getId(), trader);
    }

    /**
     * Gets trader by id.
     * @param id id.
     * @return the trader.
     */
    public Trader getTraderById(String id) {
        return traders.get(id);
    }

    /**
     * getTraders method.
     * @return the traders as a map.
     */
    public Map<String, Trader> getTraders() {
        return traders;
    }
}

