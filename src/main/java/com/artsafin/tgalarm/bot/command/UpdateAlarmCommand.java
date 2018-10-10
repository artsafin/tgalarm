package com.artsafin.tgalarm.bot.command;

public class UpdateAlarmCommand implements Command {
    public final String alarmId;
    public final String text;

    public UpdateAlarmCommand(String alarmId, String text) {
        this.alarmId = alarmId;
        this.text = text;
    }
}
