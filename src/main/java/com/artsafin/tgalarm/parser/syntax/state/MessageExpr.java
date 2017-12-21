package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.LiteralToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

import java.util.stream.Stream;

public class MessageExpr implements State {
    private Context context;

    public MessageExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (token instanceof LiteralToken) {
            context.addMessage(((LiteralToken) token).getValue());
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
