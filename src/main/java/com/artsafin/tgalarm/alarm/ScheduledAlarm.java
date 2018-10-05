package com.artsafin.tgalarm.alarm;

import com.artsafin.tgalarm.parser.EventSpec;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

public class ScheduledAlarm implements Comparable<ScheduledAlarm> {

    @Override
    public int compareTo(ScheduledAlarm other) {
        long t1 = event.getDateTime().map(ChronoZonedDateTime::toEpochSecond).orElse(0L);
        long t2 = other.event.getDateTime().map(ChronoZonedDateTime::toEpochSecond).orElse(0L);

        return (int) (t1 - t2);
    }

    private enum Status {
        ARMED,
        UNARMED,
    }

    private final ZonedDateTime created;

    private final EventSpec event;

    private final Long chatId;
    private final String userName;

    private Status status = Status.ARMED;

    private int msgId;

    public ScheduledAlarm(EventSpec event, Long chatId, String userName) {
        this.event = event;
        this.chatId = chatId;
        this.userName = userName;
        this.created = ZonedDateTime.now();
    }

    private boolean matches(final ZonedDateTime dateTime) {
        return status == Status.ARMED
                && event.getDateTime().filter(it -> it.toEpochSecond() <= dateTime.toEpochSecond()).isPresent();
    }

    public void raise(final ZonedDateTime dateTime, AbsSender sender) throws TelegramApiException {
        if (!matches(dateTime)) {
            return;
        }

        if (!event.nextPeriod().isPresent()) {
            status = Status.UNARMED;
        }

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(event.getAnnotation());

        Message msg = sender.execute(message);

        msgId = msg.getMessageId();
    }

    String getUserName() {
        return userName;
    }

    public EventSpec getEvent() {
        return event;
    }
}
