package ru.redactor.patterns.grand.structural.flyweight;

import java.util.Objects;

public class DocChar extends DocumentElement {

    private char character;

    public DocChar(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DocChar && ((DocChar)o).getCharacter() == getCharacter());
    }

    @Override
    public int hashCode() {
        return getCharacter();
    }

}
