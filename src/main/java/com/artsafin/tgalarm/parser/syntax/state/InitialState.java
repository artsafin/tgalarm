package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

import java.util.stream.Stream;

public class InitialState implements State {
    private Context context;

    public InitialState(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        return false;
    }

    @Override
    public Stream<State> nextStates() {
        return Stream.of(
                new FullDateExpr(context),
                new FullTimeExpr(context),
                new InOnAtExpr(context),
                new AfterDurationExpr(context),
                new NumberExpr(context),
                new RelativeDayExpr(context),
                new MessageExpr(context)
        );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .toString();
    }
}
