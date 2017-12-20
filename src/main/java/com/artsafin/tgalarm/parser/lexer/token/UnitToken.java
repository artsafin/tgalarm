package com.artsafin.tgalarm.parser.lexer.token;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UnitToken extends Token<ChronoUnit> {
    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    private static Pattern secPattern = Pattern.compile("^с(ек(унд[уы]?)?)?$");
    private static Pattern minPattern = Pattern.compile("^м(ин(ут[уы]?)?)?$");
    private static Pattern hourPattern = Pattern.compile("^ч(ас(а|ов)?)?$");
    private static Pattern dayPattern = Pattern.compile("^д(ень|ня|ней)?$");
    private static Pattern weekPattern = Pattern.compile("^нед(елю|ели|ель)?$");
    private static Pattern monthPattern = Pattern.compile("^мес(яц(а|ев)?)?$");
    private static Pattern yearPattern = Pattern.compile("^(г(ода?)?|лет)$");

    private static Optional<ChronoUnit> of(String value) {
        value = value.toLowerCase();

        if (secPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.SECONDS);
        }
        if (minPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.MINUTES);
        }
        if (hourPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.HOURS);
        }
        if (dayPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.DAYS);
        }
        if (weekPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.WEEKS);
        }
        if (monthPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.MONTHS);
        }
        if (yearPattern.matcher(value).matches()) {
            return Optional.of(ChronoUnit.YEARS);
        }

        return Optional.empty();
    }

    public UnitToken(String value) {
        super(of(value).get());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Token) && ((Token) obj).getValue().equals(getValue());
    }
}
