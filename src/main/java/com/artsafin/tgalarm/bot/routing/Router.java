package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.bot.user.UserSession;
import com.artsafin.tgalarm.bot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Router {
    <T extends Command> T route(Update update, UserSession session) throws UnprocessableUpdateException;
}
