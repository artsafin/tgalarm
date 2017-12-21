package com.artsafin.tgalarm.parser.lexer.token;

import java.time.Month;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class MonthNameToken extends Token<Month> {
    private static Pattern jan = Pattern.compile("^янв(ар[яье])?$");
    private static Pattern feb = Pattern.compile("^фев(рал[яье])?$");
    private static Pattern mar = Pattern.compile("^мар(т[ае]*)?$");
    private static Pattern apr = Pattern.compile("^апр(ел[яье])?$");
    private static Pattern may = Pattern.compile("^ма[ей]$");
    private static Pattern jun = Pattern.compile("^июн[яье]*$");
    private static Pattern jul = Pattern.compile("^июл[яье]*$");
    private static Pattern aug = Pattern.compile("^авг(уст[а|е]*)?$");
    private static Pattern sep = Pattern.compile("^сен(тябр[яье])?$");
    private static Pattern oct = Pattern.compile("^окт(ябр[яье])?$");
    private static Pattern nov = Pattern.compile("^ноя(бр[яье])?$");
    private static Pattern dec = Pattern.compile("^дек(абр[яье])?$");

    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    private static int strToMonthIndex(String value) {
        value = value.toLowerCase();

        if (jan.matcher(value).matches()) {
            return 1;
        } else if (feb.matcher(value).matches()) {
            return 2;
        } else if (mar.matcher(value).matches()) {
            return 3;
        } else if (apr.matcher(value).matches()) {
            return 4;
        } else if (may.matcher(value).matches()) {
            return 5;
        } else if (jun.matcher(value).matches()) {
            return 6;
        } else if (jul.matcher(value).matches()) {
            return 7;
        } else if (aug.matcher(value).matches()) {
            return 8;
        } else if (sep.matcher(value).matches()) {
            return 9;
        } else if (oct.matcher(value).matches()) {
            return 10;
        } else if (nov.matcher(value).matches()) {
            return 11;
        } else if (dec.matcher(value).matches()) {
            return 12;
        }

        return -1;
    }

    public static Optional<MonthNameToken> of(String value) {
        int month = strToMonthIndex(value);

        if (month < 0) {
            return Optional.empty();
        }

        return Optional.of(new MonthNameToken(month));
    }

    public MonthNameToken(int month) {
        super(Month.of(month));
    }
}
