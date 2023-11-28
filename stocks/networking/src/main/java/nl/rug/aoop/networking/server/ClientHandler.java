package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class for client handler.
 */
@Slf4j
@Getter
public class ClientHandler implements Runnable, Communicator {
    private Socket clientSocket;
    private int id;
    private MessageHandler messageHandler;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = false;
    private String message;

    /**
     * Constructor for this class.
     * @param clientSocket socket of the client.
     * @param id id of the client.
     * @param messageHandler message handler.
     */
    public ClientHandler(Socket clientSocket, int id, MessageHandler messageHandler) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientSocket = clientSocket;
        this.id = id;
        this.messageHandler = messageHandler;
        this.running = true;
    }

    /**
     * Method to run the client handler.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                message = in.readLine();
                if(message == null || message.trim().equalsIgnoreCase("q")) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(message);

            } catch (IOException e) {
                log.error("Error while trying to receive client " + id +" message: ", e.getMessage());
            }
        }
    }

    /**
     * Method to send a message to the client.
     * @param message message to send.
     */
    public void sendMessage(String message) {
        if (message != null) {
            out.println(message);
            out.flush();
        } else {
            log.error("Client tried to send null message");
        }

    }

    /**
     * Method to stop the client handler.
     */
    public void terminate() {
        running = false;
        closeResources();
    }

    /**
     * Method to close the resources.
     */
    private void closeResources() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                log.info("Client " + id + " socket closed");
            }
        } catch (IOException e) {
            log.error("Error while trying to close client " + id + " socket: ", e.getMessage());
        }
    }
}

