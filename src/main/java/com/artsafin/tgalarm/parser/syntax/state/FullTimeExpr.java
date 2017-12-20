package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.FullTimeToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;

import java.time.LocalTime;
import java.util.stream.Stream;

public class FullTimeExpr implements State {
    private Context context;

    public FullTimeExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (token instanceof FullTimeToken) {
            LocalTime time = ((FullTimeToken) token).getValue();

            context.setTime(time);
        }

        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(new InitialState(context));
    }
}
