package com.artsafin.tgalarm.parser.lexer;

import com.artsafin.tgalarm.parser.lexer.token.*;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class LexerTest {
    @Test
    public void name() {
        Lexer lexer = new Lexer("через пять минут будет жопа");

        List<? extends Token> tokens = lexer.lex().collect(toList());

        assertEquals(new AfterLiteralToken(), tokens.get(0));
        assertEquals(new NumberToken("5"), tokens.get(1));
        assertEquals(new UnitToken("минут"), tokens.get(2));
        assertEquals(new LiteralToken("будет"), tokens.get(3));
        assertEquals(new LiteralToken("жопа"), tokens.get(4));
    }
}