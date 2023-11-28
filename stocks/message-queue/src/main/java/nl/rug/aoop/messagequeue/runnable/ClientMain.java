package nl.rug.aoop.messagequeue.runnable;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.users.NetworkProducer;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.client.Client;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main client class.
 */
public class ClientMain {
    /**
     * Main method.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            CommandHandler commandHandler = new CommandHandler();
            MessageHandler messageHandler = new QueueMessageHandler(commandHandler);
            Client client = new Client(new InetSocketAddress("Localhost", 62000), messageHandler);
            new Thread(client).start();
            Client client2 = new Client(new InetSocketAddress("Localhost", 62000), messageHandler);
            new Thread(client2).start();
            Client client3 = new Client(new InetSocketAddress("Localhost", 62000), messageHandler);
            new Thread(client3).start();
            NetworkProducer networkProducer = new NetworkProducer(client);
            NetworkProducer networkProducer2 = new NetworkProducer(client2);
            NetworkProducer networkProducer3 = new NetworkProducer(client3);
            networkProducer.put(new Message("Test Header", "Test Message"));
            networkProducer2.put(new Message("Test Header", "Test Message2"));
            networkProducer3.put(new Message("Test Header", "Test Message3"));

        } catch (IOException ioException) {
            System.out.println("Couldn't start up client.");
        }

    }
}
