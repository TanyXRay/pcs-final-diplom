import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private static final int PORT = 8989;
    private static final Gson GSON = new Gson();
    private final SearchEngine searchEngine;

    public Server(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    /**
     * Метод запуска сервера
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Starting server at " + PORT + "...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String requestWord = in.readLine();
                    List<PageEntry> resultEntries = searchEngine.search(requestWord);

                    String responseJson = GSON.toJson(resultEntries);
                    out.println(responseJson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
