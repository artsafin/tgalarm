package com.artsafin.tgalarm.bot.command.executor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.Executor;
import com.artsafin.tgalarm.bot.command.ShowIndividualAlarmCommand;
import com.artsafin.tgalarm.bot.user.UserSession;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class ShowIndividualAlarmExecutor implements Executor {
    private final AlarmRepository alarmRepository;

    public ShowIndividualAlarmExecutor(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> execute(Command command, UserSession userSession) {
        ShowIndividualAlarmCommand cmd = (ShowIndividualAlarmCommand) command;

        Optional<ScheduledAlarm> optAlarm = alarmRepository.getById(cmd.alarmId);

        if (!optAlarm.isPresent() || optAlarm.get().userId != userSession.userId) {
            return Optional.of(new SendMessage(userSession.chatId, "Unknown alarm"));
        }

        ScheduledAlarm alarm = optAlarm.get();

        String msg = String.format(
                "Date: %s\nMessage: <b>%s</b>",
                alarm.event.timeSpecAsString().orElse("n/a"),
                alarm.event.annotation()
        );

        SendMessage response = new SendMessage(userSession.chatId, msg)
                .setReplyMarkup(
                        new InlineKeyboardMarkup()
                                .setKeyboard(Collections.singletonList(Arrays.asList(
                                        new InlineKeyboardButton("Edit").setCallbackData("{\"action\": \"edit\", \"alarmId\": \"" + cmd.alarmId + "\"}"),
                                        new InlineKeyboardButton("Delete").setCallbackData("{\"action\": \"delete\", \"alarmId\": \"" + cmd.alarmId + "\"}")
                                )))
                )
                .setParseMode("html");

        return Optional.of(response);
    }
}
