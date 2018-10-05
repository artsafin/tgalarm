package com.artsafin.tgalarm.bot.processor;

import com.artsafin.tgalarm.alarm.AlarmService;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.EventSpec;
import com.artsafin.tgalarm.parser.lexer.Lexer;
import com.artsafin.tgalarm.parser.syntax.SyntaxAnalyzer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class AlertEntryProcessor implements MessageProcessor {
    private MessageProcessor successor;

    private final AlarmService alarmService;

    public AlertEntryProcessor(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        this.successor = next;

        return this;
    }

    @Override
    public void process(Message message, AbsSender sender) throws TelegramApiException {
        String input = message.getText();

        Context context = new Context(ZonedDateTime.now());
        Lexer lexer = new Lexer(input);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        sa.analyze(lexer.lex());

        EventSpec eventSpec = context.build();
        Optional<ZonedDateTime> dateTime = eventSpec.getDateTime();

        if (!dateTime.isPresent()) {
            if (successor != null) {
                successor.process(message, sender);
            }

            return;
        }

        alarmService.add(new ScheduledAlarm(eventSpec, message.getChatId(), message.getChat().getUserName()));

        String fmtDt = dateTime.map(dt -> dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))).orElse("");

        SendMessage response = new SendMessage()
                .setParseMode("markdown")
                .setChatId(message.getChatId())
                .setText("👌\n" + fmtDt + " *" + eventSpec.getAnnotation() + "*");
        sender.execute(response);
    }
}
