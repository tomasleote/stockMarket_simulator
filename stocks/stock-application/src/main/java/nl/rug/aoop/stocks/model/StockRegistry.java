package nl.rug.aoop.stocks.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;

import java.util.*;
import com.google.gson.JsonSyntaxException;

/**
 * Class for the stocks registry.
 */
@Getter
@Setter
@Slf4j
public class StockRegistry {
    private Map<String, Stock> stocks;

    /**
     * gson used to convert to and from json.
     */
    private static Gson g;

    /**
     * Constructor of the class.
     */
    public StockRegistry(){
        this.stocks = new LinkedHashMap<>();
        g = new Gson();
    }

    /**
     * Constructor from stocks.
     * @param stocks stocks.
     */
    public StockRegistry(Map<String, Stock> stocks) {
        this.stocks = stocks;
        g = new Gson();
    }

    /**
     * Gets the stocks values.
     * @return stock values that returns.
     */
    public Collection<Stock> getStockValues(){
        return stocks.values();
    }

    /**
     * Gets stock by name.
     * @param name name of stock.
     * @return the stock.
     */
    public Stock getStockByName(String name) {
        return stocks.get(name);
    }

    /**
     * Creates a message from object.
     * @return the message created.
     */
    public Message toMessage(){
        Message message = new Message("EndOfLoading", "End");
        for(Map.Entry<String, Stock> entry: stocks.entrySet()){
            message = new Message(entry.getValue().toMessage().toJson(), message.toJson());
            message= new Message(entry.getKey(), message.toJson());
        }
        return message;
    }

    /**
     * Converts Json string to a trader Object.
     * @param str trader as Json string.
     * @return Trader.
     */
    public static StockRegistry fromJson(String str) {
        try {
            return g.fromJson(str, StockRegistry.class);
        } catch (JsonSyntaxException e) {
            log.error("Couldn't convert from Json" + str);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        try {
            return g.toJson(this);
        } catch (JsonSyntaxException e) {
            log.error("Couldn't convert to Json" + this);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a stock to the collectino.
     * @param key stock key.
     * @param stock stock model.
     */
    public void addStocks(String key, Stock stock){
        stocks.put(key, stock);
    }
}
