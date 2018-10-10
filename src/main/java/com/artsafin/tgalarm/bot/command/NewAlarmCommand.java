package com.artsafin.tgalarm.bot.command;

public class NewAlarmCommand implements Command {
    public final String text;
    public final int messageId;
    public final long chatId;

    public NewAlarmCommand(String text, int messageId, long chatId) {
        this.text = text;
        this.messageId = messageId;
        this.chatId = chatId;
    }
}
