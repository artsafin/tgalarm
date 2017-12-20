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
            new SyntaxNode(AfterLiteralToken.supports, AfterLiteralToken::new),
            new SyntaxNode(AmToken.supports, AmToken::new),
            new SyntaxNode(DayLiteralToken.supports, DayLiteralToken::new),
            new SyntaxNode(FullDateDashToken.supports, FullDateDashToken::new),
            new SyntaxNode(FullDateDotToken.supports, FullDateDotToken::new),
            new SyntaxNode(FullTimeToken.supports, FullTimeToken::new),
            new SyntaxNode(InOnAtLiteralToken.supports, InOnAtLiteralToken::new),
            new SyntaxNode(MonthNameToken.supports, MonthNameToken::new),
            new SyntaxNode(NumberToken.supports, NumberToken::new),
            new SyntaxNode(PmToken.supports, PmToken::new),
            new SyntaxNode(RelativeDayToken.supports, RelativeDayToken::new),
            new SyntaxNode(UnitToken.supports, UnitToken::new),
            new SyntaxNode(WeekdayNameToken.supports, WeekdayNameToken::new)
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
