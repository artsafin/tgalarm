package com.artsafin.tgalarm.bot.user;

public interface UserSessionRepository {
    void load(UserSession us);

    void persist(UserSession us);
}
