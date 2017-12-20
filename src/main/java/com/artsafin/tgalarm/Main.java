package com.artsafin.tgalarm;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Telegram Alarm Bot v1.0");

        Configuration config = Configuration.fromFile(getConfigPath());

        System.out.println("Link: https://t.me/" + config.getUsername());

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AlarmBot(config));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static String getConfigPath() {
        String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

        return cwd + "/config.properties";
    }
}
