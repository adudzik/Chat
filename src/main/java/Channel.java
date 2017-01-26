import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;

/**
 * Created by Arek
 * <p>
 * This abstract class is responsible for channels.
 */
abstract class Channel {
    String channelName;
    Map<Session, String> userMap = new ConcurrentHashMap<>();

    void addUser(Session session, String username) {
        userMap.put(session, username);
    }

    void removeUser(Session session) {
        userMap.remove(session);
    }

    Map<Session, String> getUserMap() {
        return userMap;
    }

    void broadcastMessage(String sender, String message) {

        this.getUserMap().keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", this.createHtmlMessageFromSender(sender, message))

                ));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void updateUserList() {
        this.getUserMap().keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userlist", this.getUserMap().values())
                ));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
                b(sender + " pisze:"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }

    public abstract void response(String msg);
}
