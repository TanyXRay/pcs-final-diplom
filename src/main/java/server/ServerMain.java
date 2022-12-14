package server;

import searching.BooleanSearchEngine;

import java.io.File;

/**
 * Класс запуска сервера,
 * обслуживающего поисковые запросы
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(engine);
        server.start();
    }
}