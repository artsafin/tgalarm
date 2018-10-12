package com.artsafin.tgalarm.bot.command.executor;

import com.artsafin.tgalarm.alarm.AlarmRepository;
import com.artsafin.tgalarm.alarm.ScheduledAlarm;
import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.Executor;
import com.artsafin.tgalarm.bot.command.NewAlarmCommand;
import com.artsafin.tgalarm.bot.user.UserSession;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.EventSpec;
import com.artsafin.tgalarm.parser.lexer.Lexer;
import com.artsafin.tgalarm.parser.syntax.SyntaxAnalyzer;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;

public class NewAlarmExecutor implements Executor {
    private final AlarmRepository alarms;

    public NewAlarmExecutor(AlarmRepository alarmRepository) {
        this.alarms = alarmRepository;
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> execute(Command command, UserSession userSession) {
        NewAlarmCommand cmd = (NewAlarmCommand) command;

        Context context = new Context(ZonedDateTime.now());
        Lexer lexer = new Lexer(cmd.text);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        sa.analyze(lexer.lex());

        EventSpec eventSpec = context.build();
        Optional<ZonedDateTime> dateTime = eventSpec.getDateTime();

        if (!dateTime.isPresent()) {
            return Optional.empty();
        }

        ScheduledAlarm alarm = new ScheduledAlarm(eventSpec, cmd.messageId, userSession.chatId, userSession.userId);

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
                .setChatId(userSession.chatId)
                .setText(htmlMessage);

        return Optional.of(response);
    }
}
