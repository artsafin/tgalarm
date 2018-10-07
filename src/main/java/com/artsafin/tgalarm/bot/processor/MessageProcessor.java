package com.artsafin.tgalarm.bot.processor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Optional;

public interface MessageProcessor {
    MessageProcessor setSuccessor(MessageProcessor next);

    Optional<? extends BotApiMethod<? extends Serializable>> process(Message message);
}
