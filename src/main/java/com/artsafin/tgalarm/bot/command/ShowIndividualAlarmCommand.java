package com.artsafin.tgalarm.bot.command;

public class ShowIndividualAlarmCommand implements Command {
    public final String alarmId;

    public ShowIndividualAlarmCommand(String alarmId) {
        this.alarmId = alarmId;
    }
}
