package com.artsafin.tgalarm.parser.syntax.state;

import com.artsafin.tgalarm.parser.lexer.token.Token;

import java.util.stream.Stream;

public interface State {
    boolean accept(Token token);

    Stream<State> nextStates();
}
