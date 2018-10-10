package com.artsafin.tgalarm.bot.command.executor;

import com.artsafin.tgalarm.bot.command.Command;
import com.artsafin.tgalarm.bot.command.Executor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.Optional;

public class FallbackExecutor implements Executor {
    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> execute(Command command) {
        return Optional.of(new SendMessage("¯\\_(ツ)_/¯"));
    }
}
