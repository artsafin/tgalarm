package com.artsafin.tgalarm;

import com.artsafin.tgalarm.alarm.AlarmService;
import com.artsafin.tgalarm.bot.AlarmBot;
import com.artsafin.tgalarm.bot.Configuration;
import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.MemoryAlarmRepository;
import com.artsafin.tgalarm.bot.processor.*;
import com.artsafin.tgalarm.ticker.TickerService;
import com.artsafin.tgalarm.ticker.TickerThread;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    static {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Telegram ScheduledAlarm Bot v1.0");

        Configuration config = Configuration.fromFile(getConfigPath());

        System.out.println("Link: https://t.me/" + config.getUsername());

        AlarmRepository alarms = new MemoryAlarmRepository();

        MessageProcessor processor =
                new AlertListProcessor(alarms)
                        .setSuccessor(new AlarmEditProcessor(alarms)
                                .setSuccessor(new AlertEntryProcessor(alarms)
                                        .setSuccessor(new FallbackProcessor())
                                ));
        AlarmBot bot = new AlarmBot(config, processor);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Thread tickerThread = new TickerThread(new TickerService(alarms, bot));

        tickerThread.start();
        System.out.println("Ticker thread started");
    }

    private static String getConfigPath() {
        String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

        return cwd + "/config.properties";
    }
}
