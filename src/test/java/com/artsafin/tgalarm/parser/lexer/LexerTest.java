package com.artsafin.tgalarm.parser.lexer;

import com.artsafin.tgalarm.parser.lexer.token.*;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class LexerTest {
    @DataProvider
    public static Object[][] dataProviderAdd() {
        return new Object[][]{
            {
                "через пять минут будет жопа",
                Arrays.asList(
                    new AfterLiteralToken("через"),
                    new NumberToken("5"),
                    new UnitToken(ChronoUnit.MINUTES),
                    new LiteralToken("будет"),
                    new LiteralToken("жопа")
                )
            },
            {
                "20 декабря заплатить за ипотеку",
                Arrays.asList(
                    new NumberToken("20"),
                    new MonthNameToken(12),
                    new LiteralToken("заплатить"),
                    new LiteralToken("за"),
                    new LiteralToken("ипотеку")
                )
            },
            {
                "завтра наконец поработать",
                Arrays.asList(
                    new RelativeDayToken(1),
                    new LiteralToken("наконец"),
                    new LiteralToken("поработать")
                )
            },
            {
                "в пн поработать",
                Arrays.asList(
                    new InOnAtLiteralToken("в"),
                    new WeekdayNameToken(1),
                    new LiteralToken("поработать")
                )
            },
            {
                "помыть слона в январе в 7 утра или вечера или дня",
                Arrays.asList(
                    new LiteralToken("помыть"),
                    new LiteralToken("слона"),
                    new InOnAtLiteralToken("в"),
                    new MonthNameToken(1),
                    new InOnAtLiteralToken("в"),
                    new NumberToken("7"),
                    new AmToken("утра"),
                    new LiteralToken("или"),
                    new PmToken("вечера"),
                    new LiteralToken("или"),
                    new PmToken("дня")
                )
            },
            {
                "помыть слона 9 числа",
                Arrays.asList(
                    new LiteralToken("помыть"),
                    new LiteralToken("слона"),
                    new NumberToken("9"),
                    new DayLiteralToken("числа")
                )
            },
            {
                "21.12.2007 в 19:12",
                Arrays.asList(
                    new FullDateDotToken(LocalDate.of(2007, 12, 21)),
                    new InOnAtLiteralToken("в"),
                    new FullTimeToken(LocalTime.of(19, 12))
                )
            },
            {
                "2007-12-21 в 19:12",
                Arrays.asList(
                    new FullDateDashToken(LocalDate.of(2007, 12, 21)),
                    new InOnAtLiteralToken("в"),
                    new FullTimeToken(LocalTime.of(19, 12))
                )
            },
        };
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void name(String input, List<Token> expectedTokens) {
        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.lex().collect(toList());

        assertEquals(expectedTokens, tokens);
    }
}