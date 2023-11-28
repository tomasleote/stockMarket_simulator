package nl.rug.aoop.stocks.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;

import java.util.*;

/**
 * Class for portfolio.
 */
@Getter
@Slf4j
public class Portfolio {

    /**
     * map containing the key as string and the number of owned shares.
     */
    private Map<String, Integer> ownedShares;

    /**
     * Constructor.
     */
    public Portfolio() {
        ownedShares = new HashMap<>();
    }

    /**
     * Constructor from message.
     * @param message message.
     */
    public Portfolio(Message message) {
        ownedShares = new HashMap<>();
        String id;
        int amount;
        while (!Objects.equals(message.getHeader(), "EndOfLoading") && !Objects.equals(message.getBody(), "End")) {
            id = message.getHeader();
            message = new Message(message.getBody());
            amount = Integer.parseInt(message.getHeader());
            message = new Message(message.getBody());
            ownedShares.put(id, amount);
        }
    }

    /**
     * constructor from map.
     * @param shares map to get shares from.
     */
    public Portfolio(Map<String, Integer> shares) {
        this.ownedShares = shares;
    }

    /**
     * Gets the names of stocks.
     * @return the names.
     */
    public List<String> getNamesOfOwnedStocks() {
        return new ArrayList<>(ownedShares.keySet());
    }

    /**
     * Removes a stock by id.
     * @param stockId id of stock.
     * @param amount  amount of stocks.
     */
    public void removeStock(String stockId, int amount) {
        if (!ownedShares.containsKey(stockId)) {
            log.error("Cannot remove stock '" + stockId + "' because the trader does not have one.");
            return;
        }

        if(ownedShares.get(stockId) < amount) {
            return;
        }

        if(ownedShares.get(stockId) >= amount) {
            int difference = ownedShares.get(stockId) - amount;
            ownedShares.remove(stockId);
            if(difference > 0) {
                ownedShares.put(stockId, difference);
                return;
            }
            return;
        }
        ownedShares.put(stockId, ownedShares.get(stockId) - amount);
    }

    /**
     * Adds a stock.
     * @param stockId id of the stock, as the name suggests.
     * @param amount  amount of stocks, as the name suggests.
     */
    public void addStock(String stockId, int amount) {
        if(ownedShares.containsKey(stockId)) {
            ownedShares.put(stockId, ownedShares.get(stockId) + amount);
        } else {
            ownedShares.put(stockId, amount);
        }
    }

    /**
     * Creates a message from object.
     * @return message created.
     */
    public Message toMessage() {
        Message message = new Message("EndOfLoading", "End");
        for (Map.Entry<String, Integer> entry : ownedShares.entrySet()) {
            message = new Message(String.valueOf(entry.getValue()), message.toJson());
            message = new Message(entry.getKey(), message.toJson());
        }
        return message;
    }
}