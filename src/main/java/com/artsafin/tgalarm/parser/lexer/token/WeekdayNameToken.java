package com.artsafin.tgalarm.parser.lexer.token;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.function.Predicate;

public class WeekdayNameToken extends Token<DayOfWeek> {
    public static Predicate<String> supports = (String chars) -> strToWeekdayIndex(chars).isPresent();

    private static Predicate<String> isMon = (String chars) -> chars.equals("пн") || chars.equals("понедельник");
    private static Predicate<String> isTue = (String chars) -> chars.equals("вт") || chars.equals("вторник");
    private static Predicate<String> isWed = (String chars) -> chars.equals("ср") || chars.equals("среда");
    private static Predicate<String> isThu = (String chars) -> chars.equals("чт") || chars.equals("четверг");
    private static Predicate<String> isFri = (String chars) -> chars.equals("пт") || chars.equals("пятница");
    private static Predicate<String> isSat = (String chars) -> chars.equals("сб") || chars.equals("суббота");
    private static Predicate<String> isSun = (String chars) -> chars.equals("вс") || chars.equals("воскресенье");

    private static Optional<Integer> strToWeekdayIndex(String value) {
        value = value.toLowerCase();

        if (isMon.test(value)) {
            return Optional.of(1);
        } else if (isTue.test(value)) {
            return Optional.of(2);
        } else if (isWed.test(value)) {
            return Optional.of(3);
        } else if (isThu.test(value)) {
            return Optional.of(4);
        } else if (isFri.test(value)) {
            return Optional.of(5);
        } else if (isSat.test(value)) {
            return Optional.of(6);
        } else if (isSun.test(value)) {
            return Optional.of(7);
        }

        return Optional.empty();
    }

    public static Optional<WeekdayNameToken> of(String value) {
        return strToWeekdayIndex(value).map(WeekdayNameToken::new);
    }

    public WeekdayNameToken(int dow) {
        super(DayOfWeek.of(dow));
    }
}
