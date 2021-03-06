package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.DateTimeMutator;
import com.artsafin.tgalarm.parser.lexer.token.*;
import com.google.common.base.MoreObjects;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class AfterDurationExpr implements State {
    private enum Status {
        NOT_STARTED,
        ACTIVE,
        WAITING_FOR_UNIT
    }

    private Context context;
    private Status status = Status.NOT_STARTED;
    private LiteralToken capturedToken;
    private NumberToken numberToken;

    public AfterDurationExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (status == Status.NOT_STARTED && token instanceof AfterLiteralToken) {
            status = Status.ACTIVE;
            capturedToken = (LiteralToken) token;

            return true;
        }

        if (status == Status.ACTIVE) {
            if (token instanceof NumberToken) {
                numberToken = (NumberToken) token;
                status = Status.WAITING_FOR_UNIT;

                return true;
            } else if (token instanceof LiteralToken) {
                context.addMessage(capturedToken.getValue());
                context.addMessage(((LiteralToken) token).getValue());
            } else {
                context.addMessage(capturedToken.getValue());
            }
        }

        if (status == Status.WAITING_FOR_UNIT) {
            if (token instanceof UnitToken) {
                ChronoUnit unit = ((UnitToken) token).getValue();

                context.withDate(it -> it.addInterval(unit.getDuration().multipliedBy(numberToken.getValue())));
            } else {
                context.addMessage(capturedToken.getValue());
                context.addMessage(numberToken.getOriginalValue());
            }
        }

        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(new InitialState(context));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("status", status)
            .add("capturedToken", capturedToken)
            .add("numberToken", numberToken)
            .toString();
    }
}
