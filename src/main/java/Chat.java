import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Arek
 * <p>
 * This class is responsible for Chat services.
 */
public class Chat {
    static Map<String, Channel> channels = new ConcurrentHashMap<>();

    void addUserChannel(Session session, String sender, String channelName) {
        channels.put(channelName, new UserChannel(session, sender, channelName));
    }

    void addBotChannel(String channelName) {
        channels.put(channelName, new ChatBot(channelName));
    }

    Channel getChannelBySession(Session session) {
        for (Channel channel : channels.values()) {
            if (channel.getUserMap().keySet().contains(session)) {
                return channel;
            }
        }
        return null;
    }
}
