package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.FullDateDashToken;
import com.artsafin.tgalarm.parser.lexer.token.FullDateDotToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;

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

        context.setDayOfMonth(date.getDayOfMonth());
        context.setMonth(date.getMonth());
        context.setYear(date.getYear());

        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(new InitialState(context));
    }
}
