package com.artsafin.tgalarm.bot.processor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class AlertListProcessor implements MessageProcessor {
    private MessageProcessor successor;

    private final AlarmRepository alarmRepository;

    public AlertListProcessor(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        this.successor = next;

        return this;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> process(Message message) {
        String txt = message.getText();

        if (!txt.startsWith("/alarms")) {
            if (successor != null) {
                return successor.process(message);
            }

            return Optional.empty();
        }

        Map<String, ScheduledAlarm> alarmList = alarmRepository.getUserAlarms(message.getFrom().getId());

        if (alarmList.isEmpty()) {
            return Optional.of(new SendMessage(message.getChatId(), "No alarms found"));
        }

        StringBuilder msg = new StringBuilder("Alarms list:\n");
        alarmList.values().stream().sorted().forEach(it -> msg.append("⚪ ")
                .append(it.getEvent().timeSpecAsString().orElse("n/a"))
                .append(": ")
                .append("<b>")
                .append(it.getEvent().annotation())
                .append("</b>")
                .append(" /alarm").append(it.id())
                .append("\n"));

        return Optional.of(new SendMessage(message.getChatId(), msg.toString()).setParseMode("html"));
    }
}
