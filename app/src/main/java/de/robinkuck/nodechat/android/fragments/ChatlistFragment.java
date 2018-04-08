package de.robinkuck.nodechat.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import de.robinkuck.nodechat.android.R;
import de.robinkuck.nodechat.android.managers.SocketManager;
import de.robinkuck.nodechat.android.views.ChatlistEntry;
import io.socket.emitter.Emitter;

public class ChatlistFragment extends Fragment {

    public LinearLayout layout;
    public LinearLayout privateChatListLayout;
    public LinearLayout globalChatLayout;

    private static ChatlistFragment INSTANCE;

    private Map<String, ChatlistEntry> entries;
    private ChatlistEntry globalChatlistEntry;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        INSTANCE = this;
        return inflater.inflate(R.layout.fragment_chatlist, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        if (v != null) {
            configViews(v);
        }
    }

    public static ChatlistFragment getInstance() {
        return INSTANCE;
    }

    public ChatlistEntry getGlobalChatlistEntry() {
        return globalChatlistEntry;
    }

    private void configViews(final View v) {
        entries = new HashMap<>();
        layout = (LinearLayout) v.findViewById(R.id.chatentrylist);
        privateChatListLayout = (LinearLayout) layout.findViewById(R.id.private_chat_list_layout);
        globalChatLayout = (LinearLayout) layout.findViewById(R.id.global_chat_layout);
        globalChatlistEntry = new ChatlistEntry(getActivity(), "global");
        globalChatlistEntry.setId(R.id.chatentry_global);
        addViewtoGlobalChatList(globalChatlistEntry);
    }

    public void configSocketEvents() {
        final Context ct = getContext();
        SocketManager.getInstance().getSocket().on("globalmessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //TODO: Implement global message event
                globalChatlistEntry.setMessageCount(globalChatlistEntry.getMessageCount() + 1);
            }
        }).on("privatemessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //TODO: Implement private message event
            }
        });
    }

    public void addViewtoPrivateChatList(final String receipient) {
        final ChatlistEntry chatlistEntry = new ChatlistEntry(getActivity(), receipient);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                privateChatListLayout.addView(chatlistEntry);
                entries.put(receipient, chatlistEntry);
            }
        });
    }

    public void removeViewFromPrivateChatList(final String nick) {
        final ChatlistEntry chatlistEntry = entries.get(nick);
        if (chatlistEntry != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    privateChatListLayout.removeView(chatlistEntry);
                }
            });
        }
    }

    public void addViewtoGlobalChatList(final View v) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                globalChatLayout.addView(v);
            }
        });
    }

    public void removeUserFromChatList(final String name) {
        if(isUserAddedToChatList(name)) {
            final ChatlistEntry chatlistEntry = entries.get(name);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    privateChatListLayout.removeView(chatlistEntry);
                }
            });
            entries.remove(name);
        }
    }

    public boolean isUserAddedToChatList(String name) {
        return entries.containsKey(name);
    }

}