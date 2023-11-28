package nl.rug.aoop.trades;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * TraderManager class, responsible for managing the traders.
 */
@Slf4j
@Getter
@Setter
public class TraderManager implements Runnable {
    private TraderRegistry traderRegistry;
    private Map<String, TraderBot> bots;
    private StockRegistry stockRegistry;
    private InetSocketAddress address;
    private ThreadSafeQueue messageQueue;
    private QueueMessageHandler traderMessageHandler;
    private boolean running;

    /**
     * Constructor.
     * @param address address to connect to.
     * @param messageQueue messageQueue.
     * @param messageHandler messageHandler.
     * @throws IOException exception handling.
     */
    public TraderManager(InetSocketAddress address, ThreadSafeQueue messageQueue, QueueMessageHandler messageHandler)
            throws IOException {
        this.address = address;
        this.messageQueue = messageQueue;
        this.traderMessageHandler = messageHandler;
        this.traderRegistry = new TraderRegistry();
        this.stockRegistry = new StockRegistry();
        this.bots = new HashMap<String, TraderBot>();
        running = false;
    }

    /**
     * startBots method, responsible for starting the bots on new threads.
     * @throws IOException exception handling.
     */
    public void startBots() throws IOException {
        for(Trader trader : traderRegistry.getTraders().values()) {
            Client client = new Client(address, traderMessageHandler);
            TraderBot bot = new TraderBot(client, trader, this);
            bots.put(trader.getId(), bot);
            new Thread(bot).start();
            log.info("Started bot for " + bot.getTrader().getName());
        }
    }

    /**
     * loadTraders method that loads the traders from the yaml file inside stonks.
     */
    public void loadTraders() {
        Path path = Path.of("./data","traders.yaml");
        YamlLoader loader = new YamlLoader(path);
        try {
            traderRegistry = loader.load(TraderRegistry.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * run method that calls the startBots method and starts to dequeue messages to be handled.
     */
    @SneakyThrows
    @Override
    public void run() {
        loadTraders();
        sleep(1000);
        running = true;
        startBots();
        while (running) {
            Message message = messageQueue.dequeue();
            receiveMessage(message);
        }
    }

    /**
     * Sends message to messageHandler.
     * @param message message received.
     */
    public void receiveMessage(Message message) {
        if (message != null) {
            traderMessageHandler.handleMessage(message.toJson());
        }
    }

    /**
     * Updates trader for bots.
     * @param trader trader to update with.
     */
    public void updateTraders(Trader trader) {
        traderRegistry.getTraderById(trader.getId()).update(trader);
        bots.get(trader.getId()).updateTrader(trader);
    }
}

