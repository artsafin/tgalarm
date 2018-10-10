package com.artsafin.tgalarm.bot;

import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.CommandExecutorFactory;
import com.artsafin.tgalarm.bot.command.Executor;
import com.artsafin.tgalarm.bot.command.UnprocessableCommandException;
import com.artsafin.tgalarm.bot.routing.Router;
import com.artsafin.tgalarm.bot.routing.UnprocessableUpdateException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Optional;

public class AlarmBot extends TelegramLongPollingBot {
    private final Configuration config;
    private final CommandExecutorFactory commandExecutor;
    private final Router router;

    public AlarmBot(Configuration config, CommandExecutorFactory commandExecutor, Router router) {
        this.config = config;
        this.commandExecutor = commandExecutor;
        this.router = router;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());

        Command command;
        Optional<? extends BotApiMethod<? extends Serializable>> response;

        try {
            command = router.route(update, new UserSession());
            Executor executor = commandExecutor.of(command);
            response = executor.execute(command);
        } catch (UnprocessableUpdateException|UnprocessableCommandException exc) {
            System.err.println("Unprocessable update: " + exc.getMessage());
            exc.printStackTrace();
            return;
        }

        response.ifPresent(it -> {
            try {
                this.execute(it);
            } catch (TelegramApiException e) {
                System.err.println("Error while sending response: " + e.getMessage());
                e.printStackTrace();
            }
        });
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
