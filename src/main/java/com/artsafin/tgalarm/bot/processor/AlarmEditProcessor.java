package com.artsafin.tgalarm.bot.processor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class AlarmEditProcessor implements MessageProcessor {
    private MessageProcessor successor;

    private final AlarmRepository alarmRepository;

    public AlarmEditProcessor(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        this.successor = next;

        return this;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> process(Message message) {

        if (!message.getText().startsWith("/alarm")) {
            if (successor != null) {
                return successor.process(message);
            }
            return Optional.empty();
        }

        String alarmId;
        try {
            alarmId = message.getText().substring(6);
        } catch (IndexOutOfBoundsException e) {
            return Optional.of(new SendMessage(message.getChatId(), "Unknown alarm"));
        }

        Optional<ScheduledAlarm> optAlarm = alarmRepository.getById(alarmId);
        if (!optAlarm.isPresent() || optAlarm.get().userId() != message.getFrom().getId()) {
            return Optional.of(new SendMessage(message.getChatId(), "Unknown alarm"));
        }

        ScheduledAlarm alarm = optAlarm.get();

        String msg = String.format(
                "Date: %s\nMessage: <b>%s</b>",
                alarm.getEvent().timeSpecAsString().orElse("n/a"),
                alarm.getEvent().annotation()
        );

        SendMessage response = new SendMessage(message.getChatId(), msg)
                .setReplyMarkup(
                        new InlineKeyboardMarkup()
                                .setKeyboard(Collections.singletonList(Arrays.asList(
                                        new InlineKeyboardButton("Edit").setCallbackData("edit=" + alarmId),
                                        new InlineKeyboardButton("Delete").setCallbackData("delete=" + alarmId)
                                )))
                )
                .setParseMode("html");

        return Optional.of(response);
    }
}
