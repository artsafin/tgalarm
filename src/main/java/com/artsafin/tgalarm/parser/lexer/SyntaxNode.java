package com.artsafin.tgalarm.parser.lexer;

import com.artsafin.tgalarm.parser.lexer.token.Token;

import java.util.function.Function;
import java.util.function.Predicate;

public class SyntaxNode implements Predicate<String>, Function<String, Token> {
    private final Predicate<String> checker;
    private final Function<String, Token> factory;

    public SyntaxNode(Predicate<String> checker, Function<String, Token> factory) {
        this.checker = checker;
        this.factory = factory;
    }

    public boolean test(String value) {
        return checker.test(value);
    }

    @Override
    public Token apply(String s) {
        return factory.apply(s);
    }
}
