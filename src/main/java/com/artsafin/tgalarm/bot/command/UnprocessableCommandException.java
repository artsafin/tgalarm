package com.artsafin.tgalarm.bot.command;

public class UnprocessableCommandException extends Exception {
    public UnprocessableCommandException(Command command) {
        super("Command not found: " + command.getClass().getName());
    }
}
