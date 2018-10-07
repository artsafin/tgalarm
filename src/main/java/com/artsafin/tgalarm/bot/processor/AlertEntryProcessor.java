package com.artsafin.tgalarm.bot.processor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.EventSpec;
import com.artsafin.tgalarm.parser.lexer.Lexer;
import com.artsafin.tgalarm.parser.syntax.SyntaxAnalyzer;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;

public class AlertEntryProcessor implements MessageProcessor {
    private MessageProcessor successor;

    private final AlarmRepository alarms;

    public AlertEntryProcessor(AlarmRepository alarms) {
        this.alarms = alarms;
    }

    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        this.successor = next;

        return this;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> process(Message message) {
        String input = message.getText();

        Context context = new Context(ZonedDateTime.now());
        Lexer lexer = new Lexer(input);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        sa.analyze(lexer.lex());

        EventSpec eventSpec = context.build();
        Optional<ZonedDateTime> dateTime = eventSpec.getDateTime();

        if (!dateTime.isPresent()) {
            if (successor != null) {
                return successor.process(message);
            }

            return Optional.empty();
        }

        ScheduledAlarm alarm = new ScheduledAlarm(eventSpec, message.getMessageId(), message.getChatId(), message.getFrom().getId());

        boolean isUpdate = alarms.has(alarm);

        alarms.addOrUpdate(alarm);

        String htmlMessage = String.format(
                "👌\n%s%s <b>%s</b>",
                isUpdate ? "edited: " : "",
                eventSpec.timeSpecAsString().orElse(""),
                eventSpec.annotation()
        );

        SendMessage response = new SendMessage()
                .setParseMode("html")
                .setChatId(message.getChatId())
                .setText(htmlMessage);

        return Optional.of(response);
    }
}
