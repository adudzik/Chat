import static spark.Spark.*;

/**
 * Created by Arek
 * Main ChatBox class
 */
public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        webSocket("/chat", ChatWebSocketHandler.class);
        new Chat().addBotChannel("Bot Boy");
        init();
    }
}
