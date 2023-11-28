package nl.rug.aoop.messagequeue.runnable;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import java.io.IOException;

/**
 * Main server class.
 */
@Slf4j
public class ServerMain {

    /**
     * Main method for the server.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        CommandHandler command = new CommandHandler();
        MessageHandler messageHandler = new QueueMessageHandler(command);
        try {
            Server server = new Server(62000, messageHandler);
            Thread serverThread = new Thread(server);
            serverThread.start();
        } catch (IOException ioException) {
            System.out.println("Server could not be started.");
        }
    }
}
