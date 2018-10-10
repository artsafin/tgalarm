package com.artsafin.tgalarm.bot.command;

public class SetEditFlowCommand implements Command {
    private final String alarmId;

    public SetEditFlowCommand(String alarmId) {
        this.alarmId = alarmId;
    }
}
