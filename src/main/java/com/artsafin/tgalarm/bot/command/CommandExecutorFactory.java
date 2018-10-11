package com.artsafin.tgalarm.bot.command;

import java.util.Map;

public class CommandExecutorFactory {
    private final Map<Class, Executor> executorMap;

    public CommandExecutorFactory(Map<Class, Executor> executorMap) {
        this.executorMap = executorMap;
    }

    public Executor of(Command command) throws UnprocessableCommandException {
        Executor executor = executorMap.get(command.getClass());

        if (executor == null) {
            throw new UnprocessableCommandException(command);
        }

        return executor;
    }
}
