package com.artsafin.tgalarm.alarm;

import com.artsafin.tgalarm.parser.EventSpec;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

public class ScheduledAlarm implements Comparable<ScheduledAlarm> {
    private enum Status {
        ARMED,
        UNARMED,
    }

    public static String createId(int userId, int msgId) {
        return userId + String.format("%05d", msgId);
    }

    public final EventSpec event;

    private final String id;
    private final int messageId;
    private final Long chatId;
    public final int userId;

    private Status status = Status.ARMED;

    private int responseMessageId;

    public ScheduledAlarm(EventSpec event, int messageId, Long chatId, int userId) {
        this.event = event;
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;

        this.id = createId(userId, messageId);
    }

    @Override
    public int compareTo(ScheduledAlarm other) {
        long t1 = event.getDateTime().map(ChronoZonedDateTime::toEpochSecond).orElse(0L);
        long t2 = other.event.getDateTime().map(ChronoZonedDateTime::toEpochSecond).orElse(0L);

        return (int) (t1 - t2);
    }

    public String id() {
        return id;
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
                .setText(event.annotation());

        Message msg = sender.execute(message);

        responseMessageId = msg.getMessageId();
    }
}
