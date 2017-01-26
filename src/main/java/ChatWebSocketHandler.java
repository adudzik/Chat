import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;

/**
 * Created by Arek
 * This class is responsible for Websockets
 */
@WebSocket
public class ChatWebSocketHandler {

    private Chat chat = new Chat();
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        user.getRemote().sendString(String.valueOf(new JSONObject()
                .put("channels", chat.channels.keySet())
        ));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        Channel channel = chat.getChannelBySession(user);
        assert channel != null;
        channel.broadcastMessage("Bot", channel.getUserMap().get(user) + " wyszedł.");
        channel.removeUser(user);
        channel.updateUserList();
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);

            if (jsonObject.has("channelName")) {
                String username = "";

                for (HttpCookie cookie : user.getUpgradeRequest().getCookies()) {
                    if (cookie.getName().equals("username"))
                        username = cookie.getValue();
                }

                String channelName = jsonObject.getString("channelName");
                if (chat.channels.containsKey(channelName))
                    chat.channels.get(channelName).addUser(user, username);
                else
                    chat.addUserChannel(user, username, channelName);

                chat.channels.get(channelName).broadcastMessage("Bot", username + " dołączył do rozmowy.");
            } else {
                if (jsonObject.has("msg")) {
                    Channel channel = chat.getChannelBySession(user);
                    assert channel != null;
                    channel.broadcastMessage(channel.getUserMap().get(user), jsonObject.getString("msg"));
                    if (channel instanceof ChatBot) {
                        channel.response(jsonObject.getString("msg"));
                    }
                }
            }
            chat.getChannelBySession(user).updateUserList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
