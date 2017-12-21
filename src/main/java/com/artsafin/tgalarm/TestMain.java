package com.artsafin.tgalarm;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

public class TestMain {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();

        now = now.plusMonths(3);

        System.out.println(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

}
