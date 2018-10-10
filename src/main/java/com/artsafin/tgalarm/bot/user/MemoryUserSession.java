package com.artsafin.tgalarm.bot.user;

import java.util.HashMap;

public class MemoryUserSession implements UserSessionRepository {
    private HashMap<Integer, UserSession> sessions = new HashMap<>();

    @Override
    public void load(UserSession us) {
        us.copyFrom(sessions.get(us.userId));
    }

    @Override
    public void persist(UserSession us) {
        sessions.put(us.userId, us);
    }
}
