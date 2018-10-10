package com.artsafin.tgalarm.bot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.Optional;

public interface Executor {
    Optional<? extends BotApiMethod<? extends Serializable>> execute(Command command);
}
