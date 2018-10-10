package com.artsafin.tgalarm.bot.command;

public class DeleteAlarmCommand implements Command {
    public final String alertId;

    public DeleteAlarmCommand(String alertId) {
        this.alertId = alertId;
    }
}
