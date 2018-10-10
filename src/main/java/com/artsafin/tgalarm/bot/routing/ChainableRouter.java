package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.bot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ChainableRouter {
    Router setSuccessor(Router successor);
    Router getSuccessor();

    default Command passToSuccessor(Update update) throws UnprocessableUpdateException {
        Router next = getSuccessor();
        if (next == null) {
            throw new UnprocessableUpdateException(this.getClass().getName() + " does not have any successor");
        }

        return next.route(update);
    }
}
