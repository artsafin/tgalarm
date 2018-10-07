package com.artsafin.tgalarm.bot;

import com.artsafin.tgalarm.bot.processor.MessageProcessor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Optional;

public class AlarmBot extends TelegramLongPollingBot {
    private final Configuration config;
    private final MessageProcessor processor;

    public AlarmBot(Configuration config, MessageProcessor processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());

        Message inputMessage = Optional.ofNullable(update.getMessage()).orElse(update.getEditedMessage());

        if (inputMessage == null) {
            System.err.println("No message in update");
            return;
        }

        if (!inputMessage.hasText()) {
            System.err.println("No text in message");
            return;
        }

        System.out.println("\tMessage:\n\t" + inputMessage.getText());

        Optional<? extends BotApiMethod<? extends Serializable>> response = processor.process(inputMessage);

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
