package com.artsafin.tgalarm.bot;

import com.artsafin.tgalarm.alarm.AlarmService;
import com.artsafin.tgalarm.bot.processor.MessageProcessor;
import com.artsafin.tgalarm.parser.EventSpec;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.Lexer;
import com.artsafin.tgalarm.parser.syntax.SyntaxAnalyzer;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        if (!update.hasMessage()) {
            return;
        }

        Message inputMessage = update.getMessage();

        if (!inputMessage.hasText()) {
            return;
        }

        System.out.println("\tMessage:\n\t" + inputMessage.getText());

        try {
            processor.process(inputMessage, this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
