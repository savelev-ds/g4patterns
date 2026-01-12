package ru.redactor.patterns.grand.behavioral.littlelanguage;

public class NotWordCombination extends Combination {

    private String word;

    /**
     * @param word Слово в строке, которого требует эта комбинация.
     */
    NotWordCombination(String word) {
        this.word = word;
    }

    /**
     * Если данная строка содержит слово, необходимое этому объекту, то метод возвращает массив смещений,
     * соответствующих появлению слова в строке.
     * В противном случае этот метод возвращает null
     */
    int[] contains(String s) {
        if (s.indexOf(word) >= 0) {
            return null;
        }
        return new int[0];
    }

}
