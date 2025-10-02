package ru.redactor.patterns.grand.behavioral.littlelanguage;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;

import static java.io.StreamTokenizer.TT_EOF;
import static java.io.StreamTokenizer.TT_WORD;

public class LexicalAnalyzer {

    private StreamTokenizer input;
    private int lastToken;

    // Константы для идентификации типа последней лексемы
    static final int INVALID_CHAR = -1;
    static final int NO_TOKEN = 0;
    static final int OR = 1;
    static final int AND = 2;
    static final int NEAR = 3;
    static final int NOT = 4;
    static final int WORD = 5;
    static final int LEFT_PAREN = 6;
    static final int RIGHT_PAREN = 7;
    static final int QUOTED_STRING = 8;
    static final int EOF = 9;

    /**
     * Конструктор
     * @param in входной поток, подлежащий считыванию.
     */
    LexicalAnalyzer(InputStream in) {
        input = new StreamTokenizer(in);
        input.resetSyntax();
        input.eolIsSignificant(false);
        input.wordChars('a', 'z');
        input.wordChars('A', 'Z');
        input.wordChars('0', '9');
        input.wordChars('\u0000', ' ');
        input.ordinaryChar('(');
        input.ordinaryChar(')');
        input.quoteChar('"');
    }

    /**
     * Возвращает строку, распознанную, как лексема слова, или тело строки, заключённой в кавычки.
     */
    String getString() {
        return input.sval;
    }

    /**
     * Возвращает тип следующей лексемы. Для лексем слова или строки в кавычках строка, представляемая
     * лексемой, может быть считана посредством вызова метода getString
     */
    int nextToken() {
        int token;
        try {
            switch (input.nextToken()) {
                case TT_EOF: token = EOF; break;
                case TT_WORD:
                    if (input.sval.equalsIgnoreCase("or"))
                        token = OR;
                    else if (input.sval.equalsIgnoreCase("and"))
                        token = AND;
                    else if (input.sval.equalsIgnoreCase("near")) {
                        token = NEAR;
                    } else if (input.sval.equalsIgnoreCase("not")) {
                        token = NOT;
                    } else {
                        token = WORD;
                    }
                    break;
                case '"': token = QUOTED_STRING; break;
                case '(': token = LEFT_PAREN; break;
                case ')': token = RIGHT_PAREN; break;
                default: token = INVALID_CHAR; break;
            }
        } catch (IOException e) {
            token = EOF;
        }
        return token;
    }

}
