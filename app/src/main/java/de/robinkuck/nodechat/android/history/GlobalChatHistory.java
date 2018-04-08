package de.robinkuck.nodechat.android.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.robinkuck.nodechat.android.fragments.ChatlistFragment;

public class GlobalChatHistory extends ChatHistory<GlobalHistoryMessage> {

    public GlobalChatHistory() {
        super("Global Chat");
    }

    public GlobalChatHistory(final JSONArray messagesList, final int unreadMessagesCount) {
        super("Global Chat");
        System.out.println("[ChatHistoryManager] creating GlobalChatHistory " + messagesList + " " + unreadMessagesCount);
        setUnreadMessagesCount(unreadMessagesCount);
        loadMessages(messagesList);
    }

    @Override
    public void addIncomingMessage(final GlobalHistoryMessage message, final boolean isReading) {
        super.addIncomingMessage(message, isReading);
        if (ChatlistFragment.getInstance().getGlobalChatlistEntry() != null && !isReading) {
            ChatlistFragment.getInstance().getGlobalChatlistEntry().setMessageCount(getUnreadMessagesCount());
        }
    }

    @Override
    public void loadMessages(final JSONArray messageList) {
        try {
            for (int i = 0; i < messageList.length(); i++) {
                JSONObject currentMessage = messageList.getJSONObject(i);
                GlobalHistoryMessage message = new GlobalHistoryMessage(currentMessage.getBoolean("personal"),
                        currentMessage.getString("dateString"), currentMessage.getString("nameString"),
                        currentMessage.getString("messageString"));
                loadHistoryMessage(message);
            }
        } catch (JSONException e) {

        }
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = new JSONObject(mapper.writeValueAsString(this));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}