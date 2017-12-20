package com.artsafin.tgalarm.parser.lexer.token;

public abstract class Token<T> {
    private T value;

    public Token(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
