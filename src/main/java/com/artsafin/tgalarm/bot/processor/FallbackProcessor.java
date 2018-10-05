package com.artsafin.tgalarm.bot.processor;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FallbackProcessor implements MessageProcessor {
    @Override
    public MessageProcessor setSuccessor(MessageProcessor next) {
        throw new RuntimeException("Fallback processor cannot have successor");
    }

    @Override
    public void process(Message message, AbsSender sender) throws TelegramApiException {
        sender.execute(new SendMessage(message.getChatId(), "¯\\_(ツ)_/¯"));
    }
}
