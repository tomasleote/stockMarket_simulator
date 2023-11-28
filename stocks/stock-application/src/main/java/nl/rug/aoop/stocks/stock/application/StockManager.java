package nl.rug.aoop.stocks.stock.application;

import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.stocks.model.*;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.orders.*;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * Stock manager class.
 * This class is used to manage the stock exchange.
 */
@Slf4j
@Getter
@Setter
public class StockManager implements Runnable {
    private final OrderHandler orderHandler;
    private Server server;
    private boolean running = false;
    private MessageQueue queue;
    private LinkedList<Order> buyQueue;
    private LinkedList<Order> sellQueue;
    private LinkedList<ResolvedOrder> resolved;
    private MarketUpdater updater;
    private TraderUpdater traderUpdater;
    private StockExchange stockExchange;
    private StockRegistry stockRegistry;
    private TraderRegistry traderRegistry;

    /**
     * Constructor.
     * @param orderHandler order handler.
     * @param stockExchange stock exchange.
     * @param queue message queue.
     * @param server server.
     * @param traderUpdater trader updater.
     */
    public StockManager(OrderHandler orderHandler, StockExchange stockExchange,
                        MessageQueue queue, Server server, TraderUpdater traderUpdater) {
        this.traderUpdater = traderUpdater;
        this.stockExchange = stockExchange;
        this.orderHandler = orderHandler;
        this.queue = queue;
        buyQueue = new LinkedList<>();
        sellQueue = new LinkedList<>();
        resolved = new LinkedList<>();
        updater = new MarketUpdater(stockExchange);
        this.server = server;
    }

    /**
     * Loads stocks from file.
     */
    public void loadStocks(){
        Path path = Path.of("./data","stocks.yaml");
        log.info(path.toAbsolutePath().toString());
        YamlLoader loader = new YamlLoader(path);
        try {
            stockExchange.setStockRegistry(loader.load(StockRegistry.class));
            updater.setStockExchange(stockExchange);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * loadTraders method, responsible for loading traders from the traders.yaml file.
     */
    public void loadTraders() {
        Path path = Path.of("./data","traders.yaml");
        log.info(path.toAbsolutePath().toString());
        YamlLoader loader = new YamlLoader(path);
        try {
            stockExchange.setTraderRegistry(loader.load(TraderRegistry.class));
            updater.setStockExchange(stockExchange);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * Logs stocks.
     */
    public void logStocks(){
        for (Stock stock : stockExchange.getStockRegistry().getStockValues()) {
            log.info("Loaded stock: " + stock.getName());
        }
    }

    /**
     * Sends stocks to the trader manager as a Json string of the list of stocks.
    */
    public void sendStocks() {
        try {
            String stocksAsJson = stockExchange.getStockRegistry().toJson();
            Message message = new Message("StocksUpdate", stocksAsJson);
            sendMessage(message.toJson(), 0);
        } catch (JsonSyntaxException e) {
            log.error("Error while converting stocks into Json: " + e.getMessage());
        }
    }

    /**
     * Sends traders to the trader manager. Matches traderId with one of the traders and sends it.
     * @param traderId trader id.
     */
    public void sendTrader(String traderId) {
        Trader trader = stockExchange.getTraderRegistry().getTraderById(traderId);
        if (trader != null) {
            String traderInJson = trader.toJson();
            Message message = new Message("TraderUpdate", traderInJson);
            sendMessage(message.toJson(), trader.getClientId());
        }
    }

    /**
     * Run method.
     * Starts the server and the communication between stock market and trader bots.
     */
    @Override
    public void run() {
        log.info("StockManager started running");
        running = true;
        traderUpdater.setServer(server);
        traderUpdater.setStockExchange(stockExchange);
        while (running) {
            checkForMessages();
            sleepThread();
        }
        traderUpdater.terminate();
    }

    /**
     * Sends a message to a client.
     * @param message message.
     * @param id id of the client.
     */
    public void sendMessage(String message, int id) {
        server.getClientHandlers().get(id).sendMessage(message);
    }

    /**
     * Terminate method.
     */
    public void terminate(){
        this.running = false;
    }

    /**
     * sleepThread method.
     */
    private void sleepThread() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checks the queue for messages and if any are found, they are handled.
     */
    private void checkForMessages() {
        if(queue.getSize() != 0){
            Message msg = queue.dequeue();
            server.getMessageHandler().handleMessage(msg.toJson());

        }
    }
}
