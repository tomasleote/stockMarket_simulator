package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Server class.
 */
@Slf4j
class TestServer {
    public static final int TIMEOUT = 1;
    @Test
    public void testRunServer() throws IOException{
        Server server = new Server(0, Mockito.mock(MessageHandler.class));
        new Thread(server).start();
        await().atMost(1, TimeUnit.SECONDS).until(server::isRunning);
        assertTrue(server.isRunning());
    }
    @Test
    public void testRunWithConnection() throws IOException {
        Server server = new Server(0, Mockito.mock(MessageHandler.class));
        new Thread(server).start();
        await().atMost(1, TimeUnit.SECONDS).until(server::isRunning);
        try(Socket socket = new Socket()) {
            assertEquals(0, server.getConnectedClients());
            socket.connect(new InetSocketAddress("localhost", server.getPort()));
            await().atMost(TIMEOUT, TimeUnit.SECONDS).until(() -> server.getConnectedClients() == 1);
            assertEquals(1, server.getConnectedClients());
        }catch (IOException e) {
            log.error("Something went wrong.", e);
        }
    }
    @Test
    public void testMultipleConnections() throws IOException {
        Server server = new Server(0, Mockito.mock(MessageHandler.class));
        new Thread(server).start();
        await().atMost(1, TimeUnit.SECONDS).until(server::isRunning);
        try(Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", server.getPort()));
            socket.connect(new InetSocketAddress("localhost", server.getPort()));
            socket.connect(new InetSocketAddress("localhost", server.getPort()));
            await().atMost(TIMEOUT, TimeUnit.SECONDS).until(() -> server.getConnectedClients() == 3);
            assertEquals(3, server.getConnectedClients());
        }catch (IOException e) {
            log.error("Something went wrong.", e);
        }
    }
}