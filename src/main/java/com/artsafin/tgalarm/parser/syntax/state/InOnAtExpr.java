package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.*;

import java.time.LocalTime;
import java.util.stream.Stream;

public class InOnAtExpr implements State {
    private enum Status {
        NOT_STARTED,
        ACTIVE,
        WAITING_FOR_AM_PM_SPEC
    }

    private Context context;
    private Status status = Status.NOT_STARTED;
    private LiteralToken capturedToken;
    private NumberToken numberToken;

    public InOnAtExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (status == Status.NOT_STARTED && token instanceof InOnAtLiteralToken) {
            status = Status.ACTIVE;
            this.capturedToken = (LiteralToken) token;

            return true;
        }

        if (status == Status.ACTIVE) {
            if (token instanceof MonthNameToken) {
                context.setMonth(((MonthNameToken) token).getValue());
            } else if (token instanceof WeekdayNameToken) {
                context.setWeekday(((WeekdayNameToken) token).getValue());
            } else if (token instanceof FullTimeToken) {
                LocalTime time = ((FullTimeToken) token).getValue();
                context.setTime(time);
            } else if (token instanceof NumberToken) {
                numberToken = (NumberToken) token;

                if (numberToken.canBe12hHours()) {
                    status = Status.WAITING_FOR_AM_PM_SPEC;
                    return true;
                } else if (numberToken.canBe24hHours()) {
                    context.setHour(numberToken.getValue());
                }
            } else {
                context.addMessage(capturedToken.getValue());
            }
        }

        if (status == Status.WAITING_FOR_AM_PM_SPEC) {
            if (token instanceof AmToken) {
                context.setHourAm(numberToken.getValue());
            } else if (token instanceof PmToken) {
                context.setHourPm(numberToken.getValue());
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
