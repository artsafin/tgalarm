package com.artsafin.tgalarm.bot.processor;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface MessageProcessor {
    MessageProcessor setSuccessor(MessageProcessor next);

    void process(Message message, AbsSender sender) throws TelegramApiException;
}
