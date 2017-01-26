
/**
 * Created by Arek
 * <p>
 * ChatBot class
 */
class ChatBot extends Channel {

    private String botName = "Bot Boy";
    private BotBoySystem chatBot = new BotBoySystem();

    ChatBot(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void response(String msg) {
        this.broadcastMessage(botName, chatBot.answer(msg));
    }

}
