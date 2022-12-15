import searching.BooleanSearchEngine;
import server.Server;

import java.io.File;

/**
 * Класс запуска сервера,
 * обслуживающего поисковые запросы
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(engine);
        server.start();
    }
}