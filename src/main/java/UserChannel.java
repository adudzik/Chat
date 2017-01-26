import org.eclipse.jetty.websocket.api.Session;

/**
 * Created by Arek
 */
class UserChannel extends Channel {

    UserChannel(Session session, String username, String channelName) {
        this.userMap.put(session, username);
        this.channelName = channelName;
    }

    public void response(String msg) {
    }
}
