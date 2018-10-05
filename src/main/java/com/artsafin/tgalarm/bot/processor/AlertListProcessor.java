package com.artsafin.tgalarm.bot.processor;

import com.artsafin.tgalarm.alarm.AlarmService;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

public class AlertListProcessor implements MessageProcessor {
    private MessageProcessor successor;

    private final AlarmService alarmService;

    public AlertListProcessor(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        this.successor = next;

        return this;
    }

    @Override
    public void process(Message message, AbsSender sender) throws TelegramApiException {
        String txt = message.getText();

        if (!txt.startsWith("/alarms")) {
            if (successor != null) {
                successor.process(message, sender);
            }

            return;
        }

        Optional<List<ScheduledAlarm>> alarmList = alarmService.getUserAlerts(message.getChat().getUserName());

        if (!alarmList.isPresent()) {
            sender.execute(new SendMessage(message.getChatId(), "No alarms found"));

            return;
        }

        StringBuilder msg = new StringBuilder("Alarms list:\n");
        alarmList.get().stream().sorted().forEach(it -> {
            msg.append("⚪ ");
            msg.append(it.getEvent().getDateTime().map(dt -> dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))).orElse("n/a"));
            msg.append(": ");
            msg.append("*");
            msg.append(it.getEvent().getAnnotation());
            msg.append("*");
            msg.append("\n");
        });

        sender.execute(new SendMessage(message.getChatId(), msg.toString()).setParseMode("markdown"));
    }
}
