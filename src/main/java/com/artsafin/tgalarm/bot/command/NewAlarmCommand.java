package com.artsafin.tgalarm.bot.command;

public class NewAlarmCommand implements Command {
    public final String text;
    public final int messageId;

    public NewAlarmCommand(String text, int messageId) {
        this.text = text;
        this.messageId = messageId;
    }
}
