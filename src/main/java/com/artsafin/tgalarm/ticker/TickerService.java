package com.artsafin.tgalarm.ticker;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZonedDateTime;

public class TickerService {
    private final AlarmRepository alarmRepository;
    private final AbsSender sender;

    public TickerService(AlarmRepository alarmRepository, AbsSender sender) {
        this.alarmRepository = alarmRepository;
        this.sender = sender;
    }

    void tick() {

        final ZonedDateTime now = ZonedDateTime.now();

        alarmRepository.forEach(alarm -> {
            try {
                alarm.raise(now, sender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}
