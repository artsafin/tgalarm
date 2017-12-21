package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class NumberToken extends Token<Integer> {
    public static Predicate<String> supports = (String chars) -> parseString(chars).isPresent();

    private static Optional<Integer> parseString(String value) {
        try {
            return Optional.of(Integer.valueOf(value));
        } catch (NumberFormatException exc) {
            // Pass to the following checks
        }

        value = value.toLowerCase();

        if (value.equals("пол")) {
            return Optional.of(0);
        }
        if (value.equals("один") || value.equals("одну")) {
            return Optional.of(1);
        }
        if (value.equals("два") || value.equals("пару") || value.equals("пара")) {
            return Optional.of(2);
        }
        if (value.equals("три")) {
            return Optional.of(3);
        }
        if (value.equals("четыре")) {
            return Optional.of(4);
        }
        if (value.equals("пять")) {
            return Optional.of(5);
        }
        if (value.equals("шесть")) {
            return Optional.of(6);
        }
        if (value.equals("семь")) {
            return Optional.of(7);
        }
        if (value.equals("восемь")) {
            return Optional.of(8);
        }
        if (value.equals("девять")) {
            return Optional.of(9);
        }
        if (value.equals("десять")) {
            return Optional.of(10);
        }
        if (value.equals("пятнадцать")) {
            return Optional.of(15);
        }
        if (value.equals("двадцать")) {
            return Optional.of(20);
        }
        if (value.equals("тридцать")) {
            return Optional.of(30);
        }

        return Optional.empty();
    }

    private String originalValue;

    public NumberToken(String value) {
        super(parseString(value).get());

        originalValue = value;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public boolean canBe12hHours() {
        return getValue() >= 1 && getValue() <= 12;
    }

    public boolean canBe24hHours() {
        return getValue() >= 0 && getValue() <= 23;
    }

    public boolean canBeDayOfMonth() {
        return getValue() >= 1 && getValue() <= 31;
    }
}
