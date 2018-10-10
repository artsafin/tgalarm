package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.bot.UserSession;
import com.artsafin.tgalarm.bot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Router {
    Command route(Update update, UserSession session) throws UnprocessableUpdateException;
}
