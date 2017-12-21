package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.RelativeDayToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

import java.time.Duration;
import java.util.stream.Stream;

public class RelativeDayExpr implements State {
    private Context context;

    public RelativeDayExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (token instanceof RelativeDayToken) {
            context.withDate(it -> it.addInterval(Duration.ofDays(((RelativeDayToken) token).getValue())));
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
