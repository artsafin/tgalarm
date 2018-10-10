package com.artsafin.tgalarm.bot.command;

import com.artsafin.tgalarm.bot.command.executor.FallbackExecutor;

import java.util.HashMap;

public class CommandExecutorFactory {
    private HashMap<Class, Executor> executorMap = new HashMap<Class, Executor>() {{
        put(DeleteAlarmCommand.class, new FallbackExecutor());
        put(NewAlarmCommand.class, new FallbackExecutor());
        put(SetEditFlowCommand.class, new FallbackExecutor());
        put(UpdateAlarmCommand.class, new FallbackExecutor());
    }};

    public Executor of(Command command) throws UnprocessableCommandException {
        Executor executor = executorMap.get(command.getClass());
        if (executor == null) {
            throw new UnprocessableCommandException(command);
        }
        return executor;
    }
}
