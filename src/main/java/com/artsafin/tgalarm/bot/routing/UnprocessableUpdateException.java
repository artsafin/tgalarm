package com.artsafin.tgalarm.bot.routing;

public class UnprocessableUpdateException extends Exception {
    public UnprocessableUpdateException(String message) {
        super(message);
    }

    public UnprocessableUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
