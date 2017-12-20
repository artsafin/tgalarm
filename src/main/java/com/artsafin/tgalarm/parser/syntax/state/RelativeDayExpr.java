package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.RelativeDayToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;

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
            context.addInterval(Duration.ofDays(((RelativeDayToken) token).getValue()));
        }

        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(new InitialState(context));
    }
}
