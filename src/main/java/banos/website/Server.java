package banos.website;

import banos.website.routes.root;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static int port = 9000;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void start() {
        Thread serverThread = new Thread(() -> {
            HttpServer server = null;
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                LOGGER.info("Server Started on " + port);
                server.createContext("/", new root());
                server.setExecutor(null);
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        serverThread.start();
    }
}
