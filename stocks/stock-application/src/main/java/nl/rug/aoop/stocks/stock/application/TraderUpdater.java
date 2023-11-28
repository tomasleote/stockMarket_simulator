package nl.rug.aoop.stocks.stock.application;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.model.StockExchange;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Trader updater class.
 * This class is used to update the traders.
 */
@Slf4j
@Getter
@Setter
public class TraderUpdater implements Runnable {
    private Server server;
    private StockExchange stockExchange;
    private TraderRegistry traders;
    private Boolean isRunning = false;

    /**
     * Constructor.
     * @param server server.
     * @param stockExchange stocks exchange.
     * @param traders traders.
     */
    public TraderUpdater(Server server, StockExchange stockExchange, TraderRegistry traders) {
        this.server = server;
        this.stockExchange = stockExchange;
        this.traders = traders;
    }

    /**
     * Run method.
     */
    @Override
    public void run() {
        isRunning = true;
        List<Trader> traderList = new ArrayList<>(traders.getTraders().values());
        while (isRunning){
            log.info("New Trader Upadate.");
            Message marketMsg = new Message("Market", stockExchange.getStockRegistry().toMessage().toJson());
            for (int i = 0; i < traders.getTraders().size(); i++) {
                ClientHandler handler = server.getClientHandlers().get(i);
                Message message = new Message(marketMsg.toJson(), traderList.get(i).toMessage().toJson());
                handler.sendMessage(new Message("MqPut",message.toJson()).toJson());
            }
        }
    }

    /**
     * Terminate method.
     */
    public void terminate(){
        isRunning = false;
    }
}