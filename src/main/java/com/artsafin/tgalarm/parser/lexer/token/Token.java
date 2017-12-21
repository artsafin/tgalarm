package com.artsafin.tgalarm.parser.lexer.token;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public abstract class Token<T> {
    private T value;

    public Token(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) obj;
        boolean bothNull = otherToken.getValue() == null && getValue() == null;
        boolean bothNotNull = otherToken.getValue() != null && getValue() != null;

        return obj.getClass().equals(getClass())
            && (bothNull || bothNotNull && getValue().equals(otherToken.getValue()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("value", value)
            .toString();
    }
}
