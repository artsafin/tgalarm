package com.artsafin.tgalarm.bot.command.executor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.Executor;
import com.artsafin.tgalarm.bot.command.ListAlarmsCommand;
import com.artsafin.tgalarm.bot.processor.MessageProcessor;
import com.artsafin.tgalarm.bot.user.UserSession;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class ListAlarmsExecutor implements Executor {
    private final AlarmRepository alarmRepository;

    public ListAlarmsExecutor(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> execute(Command command, UserSession userSession) {
        Map<String, ScheduledAlarm> alarmList = alarmRepository.getUserAlarms(userSession.userId);

        if (alarmList.isEmpty()) {
            return Optional.of(new SendMessage(userSession.chatId, "No alarms found"));
        }

        StringBuilder msg = new StringBuilder("Alarms list:\n");
        alarmList.values().stream().sorted().forEach(it -> msg.append("⚪ ")
                .append(it.event.timeSpecAsString().orElse("n/a"))
                .append(": ")
                .append("<b>")
                .append(it.event.annotation())
                .append("</b>")
                .append(" /alarm").append(it.id())
                .append("\n"));

        return Optional.of(new SendMessage(userSession.chatId, msg.toString()).setParseMode("html"));
    }
}
