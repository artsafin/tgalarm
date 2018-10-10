package com.artsafin.tgalarm.bot.user;

import java.util.HashMap;

public class UserSession extends HashMap<String, String> {
    public final int userId;
    public final long chatId;

    public UserSession(int userId, long chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public UserSession(int userId) {
        this.userId = userId;
        this.chatId = 0L;
    }

    public UserSession() {
        this.userId = 0;
        this.chatId = 0L;
    }

    void copyFrom(UserSession us) {
        this.putAll(us);
    }
}
