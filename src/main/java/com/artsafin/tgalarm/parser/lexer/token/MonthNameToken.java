package com.artsafin.tgalarm.parser.lexer.token;

import java.time.Month;
import java.util.function.Predicate;

public class MonthNameToken extends Token<Month> {
    public static Predicate<String> supports = (String chars) -> MonthNameToken.strToMonthIndex(chars) != -1;

    private static Predicate<String> isJan = (String chars) -> chars.equals("янв") || chars.equals("января") || chars.equals("январь");
    private static Predicate<String> isFeb = (String chars) -> chars.equals("фев") || chars.equals("февраля") || chars.equals("февраль");
    private static Predicate<String> isMar = (String chars) -> chars.equals("мар") || chars.equals("марта") || chars.equals("март");
    private static Predicate<String> isApr = (String chars) -> chars.equals("апр") || chars.equals("апреля") || chars.equals("апрель");
    private static Predicate<String> isMay = (String chars) -> chars.equals("май") || chars.equals("мая");
    private static Predicate<String> isJun = (String chars) -> chars.equals("июн") || chars.equals("июня") || chars.equals("июнь");
    private static Predicate<String> isJul = (String chars) -> chars.equals("июл") || chars.equals("июля") || chars.equals("июль");
    private static Predicate<String> isAug = (String chars) -> chars.equals("авг") || chars.equals("августа") || chars.equals("август");
    private static Predicate<String> isSep = (String chars) -> chars.equals("сен") || chars.equals("сентября") || chars.equals("сентябрь");
    private static Predicate<String> isOct = (String chars) -> chars.equals("окт") || chars.equals("октября") || chars.equals("октябрь");
    private static Predicate<String> isNov = (String chars) -> chars.equals("ноя") || chars.equals("ноября") || chars.equals("ноябрь");
    private static Predicate<String> isDec = (String chars) -> chars.equals("дек") || chars.equals("декабря") || chars.equals("декабрь");

    private static int strToMonthIndex(String value) {
        value = value.toLowerCase();

        if (isJan.test(value)) {
            return 1;
        } else if (isFeb.test(value)) {
            return 2;
        } else if (isMar.test(value)) {
            return 3;
        } else if (isApr.test(value)) {
            return 4;
        } else if (isMay.test(value)) {
            return 5;
        } else if (isJun.test(value)) {
            return 6;
        } else if (isJul.test(value)) {
            return 7;
        } else if (isAug.test(value)) {
            return 8;
        } else if (isSep.test(value)) {
            return 9;
        } else if (isOct.test(value)) {
            return 10;
        } else if (isNov.test(value)) {
            return 11;
        } else if (isDec.test(value)) {
            return 12;
        }

        return -1;
    }

    public MonthNameToken(String value) {
        super(Month.of(strToMonthIndex(value)));
    }
}
