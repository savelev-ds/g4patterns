package ru.redactor.patterns.grand.behavioral.littlelanguage;

import java.io.InputStream;

public class Parser {

    private LexicalAnalyzer lexer;
    private int token;

    /**
     * Синтаксический анализ комбинации слов, прочитанных из данного входного потока
     * @return Объект комбинации, который является корнем синтаксического дерева
     */
    public Combination parse(InputStream input) {
        lexer = new LexicalAnalyzer(input);
        Combination c = orCombination();
        expect(LexicalAnalyzer.EOF);
        return c;
    }

    private Combination orCombination() throws SyntaxException {
        Combination c = andCombination();
        while (token == LexicalAnalyzer.OR) {
            c = new OrCombination(c, andCombination());
        }
        return c;
    }

    private Combination andCombination() throws SyntaxException {
        Combination c = nearCombination();
        while (token == LexicalAnalyzer.AND) {
            c = new AndCombination(c, nearCombination());
        }
        return c;
    }

    private Combination nearCombination() throws SyntaxException {
        Combination c = simpleCombination();
        while (token == LexicalAnalyzer.NEAR) {
            c = new NearCombination(c, simpleCombination());
        }
        return c;
    }

    private Combination simpleCombination() throws SyntaxException {
        if (token == LexicalAnalyzer.LEFT_PAREN) {
            nextToken();
            Combination c = orCombination();
            expect(LexicalAnalyzer.RIGHT_PAREN);
            return c;
        }
        if (token == LexicalAnalyzer.NOT) {
            return notWordCombination();
        } else {
            return wordCombination();
        }
    }

    private Combination wordCombination() throws SyntaxException {
        if (token != LexicalAnalyzer.WORD && token != LexicalAnalyzer.QUOTED_STRING) {
            expect(LexicalAnalyzer.WORD);
        }
        Combination c = new WordCombination(lexer.getString());
        nextToken();
        return c;
    }

    private Combination notWordCombination() throws SyntaxException {
        expect(LexicalAnalyzer.NOT);
        if (token != LexicalAnalyzer.QUOTED_STRING && token != LexicalAnalyzer.WORD) {
            expect(LexicalAnalyzer.WORD);
        }
        Combination c;
        c = new NotWordCombination(lexer.getString());
        nextToken();
        return c;
    }

    private void nextToken() throws SyntaxException {
        token = lexer.nextToken();
    }

    private void expect(int t) throws SyntaxException {
        if (t != token) {
            throw new SyntaxException("Expected " + t + " but got " + token);
        }
        nextToken();
    }

    private String tokenName(int t) {
        String tName;
        switch (t) {
            case LexicalAnalyzer.LEFT_PAREN: tName = "("; break;
            case LexicalAnalyzer.RIGHT_PAREN: tName = ")"; break;
            case LexicalAnalyzer.WORD: tName = "WORD"; break;
            case LexicalAnalyzer.QUOTED_STRING: tName = "quoted_string"; break;
            case LexicalAnalyzer.NOT: tName = "NOT"; break;
            case LexicalAnalyzer.OR: tName = "OR"; break;
            case LexicalAnalyzer.AND: tName = "AND"; break;
            case LexicalAnalyzer.NEAR: tName = "NEAR"; break;
            case LexicalAnalyzer.EOF: tName = "end of file"; break;
            default: tName = "unknown"; break;
        }
        return tName;
    }


}
