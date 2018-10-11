package com.artsafin.tgalarm.bot.routing;

import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.bot.command.*;
import com.artsafin.tgalarm.bot.user.UserSession;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageRouter implements Router, ChainableRouter {
    private Router successor;

    @Override
    public Command route(Update update, UserSession session) throws UnprocessableUpdateException {
        Message inputMessage = Optional.ofNullable(update.getMessage()).orElse(update.getEditedMessage());

        if (inputMessage == null || !inputMessage.hasText()) {
            return passToSuccessor(update, session);
        }

        if (update.hasEditedMessage()) {
            return new UpdateAlarmCommand(
                    ScheduledAlarm.createId(session.userId, update.getEditedMessage().getMessageId()),
                    inputMessage.getText()
            );
        }

        if (inputMessage.getText().equals("/alarms")) {
            return new ListAlarmsCommand();
        }

        Matcher alarmMatcher = Pattern.compile("/alarm(\\d+)").matcher(inputMessage.getText());
        if (alarmMatcher.matches()) {
            return new ShowIndividualAlarmCommand(alarmMatcher.group(1));
        }

        return new NewAlarmCommand(inputMessage.getText(), update.getMessage().getMessageId());
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
