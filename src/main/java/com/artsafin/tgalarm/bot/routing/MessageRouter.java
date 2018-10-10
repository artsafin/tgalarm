package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.bot.user.UserSession;
import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.NewAlarmCommand;
import com.artsafin.tgalarm.bot.command.UpdateAlarmCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class MessageRouter implements Router, ChainableRouter {
    private Router successor;

    @Override
    public Command route(Update update, UserSession session) throws UnprocessableUpdateException {
        Message inputMessage = Optional.ofNullable(update.getMessage()).orElse(update.getEditedMessage());

        if (inputMessage == null || !inputMessage.hasText()) {
            return passToSuccessor(update);
        }

        if (update.hasEditedMessage()) {
            return new UpdateAlarmCommand("azaza", "ololo");
        }

        if (update.hasMessage()) {
            return new NewAlarmCommand(inputMessage.getText());
        }

        return passToSuccessor(update);
    }

    @Override
    public Router setSuccessor(Router successor) {
        this.successor = successor;
        return this;
    }

    @Override
    public Router getSuccessor() {
        return successor;
    }
}
