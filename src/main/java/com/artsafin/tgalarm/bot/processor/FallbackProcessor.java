package com.artsafin.tgalarm.bot.processor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.Optional;

public class FallbackProcessor implements MessageProcessor {
    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        throw new RuntimeException("Fallback processor cannot have a successor");
    }

    @Override
    public Optional<? extends BotApiMethod<? extends Serializable>> process(Message message) {
        return Optional.of(new SendMessage(message.getChatId(), "¯\\_(ツ)_/¯"));
    }
}
