package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.FullTimeToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

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

            context.withDate(it -> it.setTime(time));
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
            .toString();
    }
}
