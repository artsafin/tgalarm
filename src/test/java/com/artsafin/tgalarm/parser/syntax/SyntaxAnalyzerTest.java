package com.artsafin.tgalarm.parser.syntax;

import com.artsafin.tgalarm.parser.AnnotatedDateTime;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.*;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class SyntaxAnalyzerTest {
    @Test
    public void name() {
        ZonedDateTime now = ZonedDateTime.parse("2017-12-21T12:34:56.00000+00:00");
        Context context = new Context(now);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        Stream<Token> tokenStream = Stream.of(
            new AfterLiteralToken("через"),
            new NumberToken("5"),
            new UnitToken(ChronoUnit.MINUTES),
            new LiteralToken("будет"),
            new LiteralToken("жопа")
        );

        sa.analyze(tokenStream);

        AnnotatedDateTime annotatedDateTime = context.build();

        assertEquals("будет жопа", annotatedDateTime.getAnnotation());
        assertEquals("2017-12-21T12:39:56Z", annotatedDateTime.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}