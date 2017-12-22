package com.artsafin.tgalarm;

import com.artsafin.tgalarm.parser.AnnotatedDateTime;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.Lexer;
import com.artsafin.tgalarm.parser.syntax.SyntaxAnalyzer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AlarmBot extends TelegramLongPollingBot {
    private final Configuration config;

    public AlarmBot(Configuration config) {
        this.config = config;
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

        String response = getResponse(inputMessage.getText());

        SendMessage message = new SendMessage()
                .setChatId(inputMessage.getChatId())
                .setText(response);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(String input) {
        ZonedDateTime now = ZonedDateTime.now();
        Context context = new Context(now);

        Lexer lexer = new Lexer(input);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        sa.analyze(lexer.lex());

        AnnotatedDateTime annotatedDateTime = context.build();

        return
                "Date: "
                        + annotatedDateTime.getDateTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                + "\n"
                + "Message: " + annotatedDateTime.getAnnotation();
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
