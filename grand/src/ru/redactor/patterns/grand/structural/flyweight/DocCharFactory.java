package ru.redactor.patterns.grand.structural.flyweight;

import java.util.HashMap;

public class DocCharFactory {

    private MutableDocChar myChar = new MutableDocChar();

    private HashMap docCharPool = new HashMap();

    synchronized DocChar getDocChar(char c) {
        myChar.setCharacter(c);
        DocChar thisChar = (DocChar)docCharPool.get(myChar);
        if (thisChar == null) {
            thisChar = new DocChar(c);
            docCharPool.put(thisChar, thisChar);
        }
        return thisChar;
    }

    private static class MutableDocChar extends DocChar {

        private char character;

        MutableDocChar() {
            super('a');
        }

        @Override
        public char getCharacter() {
            return character;
        }

        public void setCharacter(char character) {
            this.character = character;
        }

    }

}
