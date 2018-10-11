package com.artsafin.tgalarm;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.MemoryAlarmRepository;
import com.artsafin.tgalarm.bot.AlarmBot;
import com.artsafin.tgalarm.bot.Configuration;
import com.artsafin.tgalarm.bot.command.*;
import com.artsafin.tgalarm.bot.command.executor.FallbackExecutor;
import com.artsafin.tgalarm.bot.command.executor.ListAlarmsExecutor;
import com.artsafin.tgalarm.bot.command.executor.ShowIndividualAlarmExecutor;
import com.artsafin.tgalarm.bot.routing.CallbackQueryRouter;
import com.artsafin.tgalarm.bot.routing.MessageRouter;
import com.artsafin.tgalarm.bot.routing.Router;
import com.artsafin.tgalarm.bot.user.MemoryUserSession;
import com.artsafin.tgalarm.bot.user.UserSessionRepository;
import com.artsafin.tgalarm.ticker.TickerService;
import com.artsafin.tgalarm.ticker.TickerThread;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    static {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Telegram ScheduledAlarm Bot v1.0");

        Configuration config = Configuration.fromFile(getConfigPath());

        System.out.println("Link: https://t.me/" + config.getUsername());

        AlarmRepository alarms = new MemoryAlarmRepository();
        UserSessionRepository usRepo = new MemoryUserSession();

        Router routerChain = new CallbackQueryRouter().setSuccessor(new MessageRouter());
        CommandExecutorFactory executorFactory = new CommandExecutorFactory(new HashMap<Class, Executor>() {{
            put(DeleteAlarmCommand.class, new FallbackExecutor());
            put(NewAlarmCommand.class, new FallbackExecutor());
            put(SetEditFlowCommand.class, new FallbackExecutor());
            put(UpdateAlarmCommand.class, new FallbackExecutor());
            put(ListAlarmsCommand.class, new ListAlarmsExecutor(alarms));
            put(ShowIndividualAlarmCommand.class, new ShowIndividualAlarmExecutor(alarms));
        }});

        AlarmBot bot = new AlarmBot(config, executorFactory, routerChain, usRepo);

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
