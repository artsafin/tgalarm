package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.FullDateDashToken;
import com.artsafin.tgalarm.parser.lexer.token.FullDateDotToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.util.stream.Stream;

public class FullDateExpr implements State {
    private Context context;

    public FullDateExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        LocalDate date;

        if (token instanceof FullDateDashToken) {
            date = ((FullDateDashToken) token).getValue();
        } else if (token instanceof FullDateDotToken) {
            date = ((FullDateDotToken) token).getValue();
        } else {
            return false;
        }

        context.withDate(it -> {
            it.setDayOfMonth(date.getDayOfMonth());
            it.setMonth(date.getMonth());
            it.setYear(date.getYear());
        });

        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(new InitialState(context));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .toString();
    }
}
