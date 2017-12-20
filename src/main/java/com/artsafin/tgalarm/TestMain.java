package com.artsafin.tgalarm;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestMain {
    public static void main(String[] args) {
        String str = "this is a big string";

        Stream<String> stream = Arrays.stream(str.split("\\s+"));

        stream.forEach(System.out::println);
    }
}
