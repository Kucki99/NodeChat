package de.robinkuck.nodechat.android.views;

import android.content.Context;

public class GlobalChatForeignMessageView extends ForeignMessageView {

    public GlobalChatForeignMessageView(final Context ct, final String message, final String name, final int size) {
        super(ct, message, name, size);
        createMessageText();
    }

    @Override
    protected  void createMessageText() {
        super.createMessageText();
        super.addName();
    }

}
