package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.DayLiteralToken;
import com.artsafin.tgalarm.parser.lexer.token.MonthNameToken;
import com.artsafin.tgalarm.parser.lexer.token.NumberToken;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.google.common.base.MoreObjects;

import java.util.stream.Stream;

public class NumberExpr implements State {
    private Context context;

    private NumberToken numberToken;

    public NumberExpr(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(Token token) {
        if (numberToken == null && token instanceof NumberToken && ((NumberToken) token).canBeDayOfMonth()) {
            numberToken = (NumberToken) token;

            return true;
        }

        if (numberToken != null) {
            if (token instanceof DayLiteralToken) {
                context.withDate(it -> it.setDayOfMonth(numberToken.getValue()));
            } else if (token instanceof MonthNameToken) {
                context.withDate(it -> {
                    it.setDayOfMonth(numberToken.getValue());
                    it.setMonth(((MonthNameToken) token).getValue());
                });
            } else {
                context.addMessage(numberToken.getOriginalValue());
            }
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
            .add("numberToken", numberToken)
            .toString();
    }
}
