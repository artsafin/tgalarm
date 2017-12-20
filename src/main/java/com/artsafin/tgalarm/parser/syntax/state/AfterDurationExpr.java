package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.*;

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
            } else {
                context.addMessage(capturedToken.getValue());
            }
        }

        if (status == Status.WAITING_FOR_UNIT) {
            if (token instanceof UnitToken) {
                ChronoUnit unit = ((UnitToken) token).getValue();

                context.addInterval(unit.getDuration().multipliedBy(numberToken.getValue()));
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
}
