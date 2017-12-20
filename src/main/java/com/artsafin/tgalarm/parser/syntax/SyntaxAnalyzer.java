package com.artsafin.tgalarm.parser.syntax;

import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.Token;
import com.artsafin.tgalarm.parser.syntax.state.InitialState;
import com.artsafin.tgalarm.parser.syntax.state.State;

import java.util.Optional;
import java.util.stream.Stream;

public class SyntaxAnalyzer {
    private final Context context;
    private State state;

    public SyntaxAnalyzer(Context context) {
        state = new InitialState(context);
        this.context = context;
    }

    public void analyze(Stream<Token> tokens) {
        tokens.forEach((Token token) -> {
            boolean accepted = state.accept(token);

            if (!accepted) {
                Optional<State> foundState = state.nextStates()
                        .filter((State next) -> next.accept(token))
                        .findFirst();

                state = foundState.orElseGet(() -> new InitialState(context));
            }
        });


    }
}
