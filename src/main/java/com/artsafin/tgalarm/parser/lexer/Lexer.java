package com.artsafin.tgalarm.parser.lexer;

import com.artsafin.tgalarm.parser.lexer.token.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Lexer {
    private String input;

    private List<SyntaxNode> syntax = Arrays.asList(
            new SyntaxNode(AfterLiteralToken.supports, AfterLiteralToken::of),
            new SyntaxNode(AmToken.supports, AmToken::of),
            new SyntaxNode(DayLiteralToken.supports, DayLiteralToken::of),
            new SyntaxNode(FullDateDashToken.supports, FullDateDashToken::of),
            new SyntaxNode(FullDateDotToken.supports, FullDateDotToken::of),
            new SyntaxNode(FullTimeToken.supports, FullTimeToken::of),
            new SyntaxNode(InOnAtLiteralToken.supports, InOnAtLiteralToken::of),
            new SyntaxNode(MonthNameToken.supports, MonthNameToken::of),
            new SyntaxNode(NumberToken.supports, s -> Optional.of(new NumberToken(s))),
            new SyntaxNode(PmToken.supports, PmToken::of),
            new SyntaxNode(RelativeDayToken.supports, RelativeDayToken::of),
            new SyntaxNode(UnitToken.supports, UnitToken::of),
            new SyntaxNode(WeekdayNameToken.supports, WeekdayNameToken::of)
    );

    public Lexer(String input) {
        this.input = input;
    }

    public Stream<? extends Token> lex() {
        Stream<String> wordStream = Arrays.stream(input.split("\\s+"));

        return wordStream
                .map((String word) -> {
                    Optional<SyntaxNode> node = syntax
                            .stream()
                            .filter((SyntaxNode d) -> d.test(word))
                            .findFirst();

                    if (node.isPresent()) {
                        return node.get().apply(word);
                    }

                    return new LiteralToken(word);
                })
                .filter(Objects::nonNull);
    }
}
