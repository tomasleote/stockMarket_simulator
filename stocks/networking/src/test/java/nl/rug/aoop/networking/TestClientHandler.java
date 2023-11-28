package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.server.ClientHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for ClientHandler.
 */
@Slf4j
public class TestClientHandler {

    private int port;
    private boolean serverStarted = false;
    private PrintWriter out;
    private BufferedReader in;
    private Socket serverSocket = null;

    private void startServer(){
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                port = s.getLocalPort();
                serverStarted = true;

                serverSocket = s.accept();
                log.info("Server created");

            }catch (IOException e){
                log.error("Couldn't make server", e);
            }
        }).start();

        await().atMost(2, TimeUnit.SECONDS).until(()->serverStarted);
    }
    @Test
    public void testRun() throws IOException{
        startServer();
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("localhost", port));
            await().atMost(1, TimeUnit.SECONDS).until(()->serverSocket != null);
            MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
            ClientHandler clientHandler = new ClientHandler(serverSocket, 1, mockHandler);
            new Thread(clientHandler).start();
            await().atMost(1, TimeUnit.SECONDS).until(clientHandler::isRunning);
            assertTrue(clientHandler.isRunning());
            String message = "Test";

            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            serverOut.println(message);
            serverOut.flush();
            await().atMost(2, TimeUnit.SECONDS).with()
                    .pollInterval(10, TimeUnit.MILLISECONDS)
                    .until(()->clientHandler.getMessage() != null);

            Mockito.verify(mockHandler).handleMessage(message);
        }
    }

    @Test
    public void testNullMessage() throws IOException{
        startServer();
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("localhost", port));
            await().atMost(1, TimeUnit.SECONDS).until(()->serverSocket != null);
            MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
            ClientHandler clientHandler = new ClientHandler(serverSocket, 1, mockHandler);
            new Thread(clientHandler).start();
            await().atMost(1, TimeUnit.SECONDS).until(clientHandler::isRunning);
            assertTrue(clientHandler.isRunning());
            String message = null;

            assertDoesNotThrow(
                    () -> mockHandler.handleMessage(message),
                    "Null message."
            );
        }
    }
}
