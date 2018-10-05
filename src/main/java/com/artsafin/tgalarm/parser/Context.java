package com.artsafin.tgalarm.parser;

import java.time.*;
import java.util.function.Consumer;

public class Context {
    private boolean canHavePrefix = true;

    private ZonedDateTime now;
    private DateTimeMutator mutator = new DateTimeMutator();
    private StringBuilder prefixMessage = new StringBuilder();
    private StringBuilder suffixMessage = new StringBuilder();

    public Context(ZonedDateTime now) {
        this.now = now;
    }

    public void withDate(Consumer<DateTimeMutator> consumer) {
        consumer.accept(mutator);
        markMutated();
    }

    private void markMutated() {
        canHavePrefix = false;

        if (suffixMessage.length() > 0) {
            suffixMessage = new StringBuilder();
        }
    }

    public void addMessage(String value) {
        if (canHavePrefix) {
            if (prefixMessage.length() != 0) {
                prefixMessage.append(" ");
            }
            prefixMessage.append(value);
        } else {
            if (suffixMessage.length() != 0) {
                suffixMessage.append(" ");
            }

            suffixMessage.append(value);
        }
    }

    public EventSpec build() {
        String message = prefixMessage
            .append((prefixMessage.length() > 0 && suffixMessage.length() > 0 ? " " : ""))
            .append(suffixMessage)
            .toString();

        return new EventSpec(mutator.build(now).orElse(null), message);
    }
}
