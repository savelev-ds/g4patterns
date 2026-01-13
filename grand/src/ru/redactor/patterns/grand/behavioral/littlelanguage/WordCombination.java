package ru.redactor.patterns.grand.behavioral.littlelanguage;

public class WordCombination extends Combination {

    public WordCombination(Combination c, Combination combination) {

    }

    public WordCombination(String string) {

    }

    @Override
    int[] contains(String str) {
        return new int[0];
    }

}
