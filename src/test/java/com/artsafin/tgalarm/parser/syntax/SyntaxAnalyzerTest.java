package com.artsafin.tgalarm.parser.syntax;

import com.artsafin.tgalarm.parser.AnnotatedDateTime;
import com.artsafin.tgalarm.parser.Context;
import com.artsafin.tgalarm.parser.lexer.token.*;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class SyntaxAnalyzerTest {
    private ZonedDateTime now = ZonedDateTime.parse("2017-12-21T12:34:56.00000+00:00");

    @DataProvider
    public static Object[][] tokensProvider() {
        return new Object[][]{
            {
                Stream.of(
                    new AfterLiteralToken("через"),
                    new NumberToken("5"),
                    new UnitToken(ChronoUnit.MINUTES),
                    new LiteralToken("будет"),
                    new LiteralToken("жопа")
                ),
                "будет жопа",
                "2017-12-21T12:39:56Z"
            },
            {
                Stream.of(
                    new RelativeDayToken(1),
                    new InOnAtLiteralToken("в"),
                    new NumberToken("7"),
                    new PmToken("вечера"),
                    new LiteralToken("жахнуть"),
                    new NumberToken("50"),
                    new LiteralToken("грамм")
                ),
                "жахнуть 50 грамм",
                "2017-12-22T19:00:00Z"
            },
            {
                Stream.of(
                    new RelativeDayToken(1),
                    new InOnAtLiteralToken("в"),
                    new NumberToken("7"),
                    new LiteralToken("жахнуть"),
                    new NumberToken("50"),
                    new LiteralToken("грамм")
                ),
                "жахнуть 50 грамм",
                "2017-12-22T07:00:00Z"
            },
        };
    }

    @Test
    @UseDataProvider("tokensProvider")
    public void testTokenStreamIsProcessedBySyntaxAnalyzer(Stream<Token> tokens, String message, String dateFormatted) {
        Context context = new Context(now);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(context);

        sa.analyze(tokens);

        AnnotatedDateTime annotatedDateTime = context.build();

        assertEquals(message, annotatedDateTime.getAnnotation());

        Optional<ZonedDateTime> dt = annotatedDateTime.getDateTime();
        assertTrue(dt.isPresent());
        assertEquals(dateFormatted, dt.get().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}