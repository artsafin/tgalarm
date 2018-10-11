package com.artsafin.tgalarm.bot;

import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.CommandExecutorFactory;
import com.artsafin.tgalarm.bot.command.Executor;
import com.artsafin.tgalarm.bot.command.UnprocessableCommandException;
import com.artsafin.tgalarm.bot.routing.Router;
import com.artsafin.tgalarm.bot.routing.UnprocessableUpdateException;
import com.artsafin.tgalarm.bot.user.UserSession;
import com.artsafin.tgalarm.bot.user.UserSessionRepository;
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
    private final UserSessionRepository userSessionRepository;

    public AlarmBot(Configuration config, CommandExecutorFactory commandExecutor, Router router, UserSessionRepository userSessionRepository) {
        this.config = config;
        this.commandExecutor = commandExecutor;
        this.router = router;
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());

        Optional<? extends BotApiMethod<? extends Serializable>> response;

        UserSession us = createUserSession(update);
        userSessionRepository.load(us);

        try {
            Command command = router.route(update, us);
            System.out.println("Command: " + command.getClass().getName());

            Executor executor = commandExecutor.of(command);
            response = executor.execute(command, us);

            userSessionRepository.persist(us);
        } catch (UnprocessableUpdateException | UnprocessableCommandException exc) {
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

    private UserSession createUserSession(Update update) {
        if (update.getCallbackQuery() != null) {
            return new UserSession(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getChatId());
        }
        if (update.getMessage() != null) {
            return new UserSession(update.getMessage().getFrom().getId(), update.getMessage().getChatId());
        }
        if (update.getEditedMessage() != null) {
            return new UserSession(update.getEditedMessage().getFrom().getId(), update.getEditedMessage().getChatId());
        }
        if (update.getChannelPost() != null) {
            return new UserSession(update.getChannelPost().getFrom().getId(), update.getChannelPost().getChatId());
        }
        if (update.getEditedChannelPost() != null) {
            return new UserSession(update.getEditedChannelPost().getFrom().getId(), update.getEditedChannelPost().getChatId());
        }
        if (update.getChosenInlineQuery() != null) {
            return new UserSession(update.getChosenInlineQuery().getFrom().getId());
        }
        if (update.getInlineQuery() != null) {
            return new UserSession(update.getInlineQuery().getFrom().getId());
        }

        return new UserSession();
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
