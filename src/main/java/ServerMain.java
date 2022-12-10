import java.io.File;

/**
 * Сейчас в нём находится заготовка использования поискового движка.
 * После его реализации, содержимое main нужно будет заменить на запуск сервера,
 * обслуживающего поисковые запросы
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(engine);
        server.start();
    }
}