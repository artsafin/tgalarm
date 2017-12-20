package com.artsafin.tgalarm;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class AlarmBot extends TelegramLongPollingBot {
    private final Configuration config;

    public AlarmBot(Configuration config) {
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
