package nl.rug.aoop.stocks.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.model.TraderDataModel;

import java.util.List;
import java.util.Map;

/**
 * Class for trader model.
 */
@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class Trader implements TraderDataModel {
    private String id;
    private String name;
    private double funds;
    private Portfolio stockPortfolio;
    private int clientId;
    private static Gson g;

    /**
     * Constructor.
     * @param id id of the trader.
     * @param name name of the trader.
     * @param funds initial funds.
     * @param portfolio portfolio containing owned stocks.
     * @param clientId client id;
     */
    public Trader(String id, String name, double funds, Portfolio portfolio, int clientId) {
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.stockPortfolio = portfolio;
        this.clientId = clientId;
        g = new Gson();

    }

    /**
     * Gets owned stocks.
     * @return the stocks as string.
     */
    @Override
    public List<String> getOwnedStocks() {
        return stockPortfolio.getNamesOfOwnedStocks();
    }

    /**
     * Get owned stocks.
     * @return stocks as map.
     */
    public Map<String, Integer> getOwnedStocksInPortfolio(){
        return stockPortfolio.getOwnedShares();
    }

    /**
     * Sell stock.
     * @param stockId id-
     * @param amount amount.
     */
    public void sell(String stockId, int amount){
        stockPortfolio.removeStock(stockId, amount);
    }

    /**
     * Buy stock.
     * @param stockId id.
     * @param amount amount.
     */
    public void buy(String stockId, int amount){
        stockPortfolio.addStock(stockId, amount);
    }

    /**
     * Creates messsage object.
     * @return message.
     */
    public Message toMessage(){
        Message fundsAndPortfolio = new Message( String.valueOf(funds), stockPortfolio.toMessage().toJson());
        Message nameMsg = new Message(name,fundsAndPortfolio.toJson());
        return new Message(id, nameMsg.toJson());
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        g = new Gson();
        try {
            return g.toJson(this);
        } catch (JsonSyntaxException e) {
            log.error("Couldn't convert to Json" + this);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts Json string to a trader Object.
     * @param str trader as Json string.
     * @return Trader.
     */
    public static Trader fromJson(String str) {
        g = new Gson();
        try {
            return g.fromJson(str, Trader.class);
        } catch (JsonSyntaxException e) {
            log.error("Couldn't convert from Json" + str);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates trader.
     * @param trader trader.
     */
    public void update(Trader trader){
        this.funds = trader.getFunds();
        this.stockPortfolio = trader.getStockPortfolio();
    }
}