package ru.redactor.patterns.grand.behavioral.littlelanguage;

import java.util.Arrays;

public class AndCombination extends Combination {

    private Combination leftChild, rightChild;

    AndCombination(Combination leftChild, Combination rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    int[] contains(String s) {
        int[] leftResult = leftChild.contains(s);
        int[] rightResult = rightChild.contains(s);
        if (leftResult == null || rightResult == null) {
            return null;
        }
        if (leftResult.length == 0)
            return rightResult;
        if (rightResult.length == 0)
            return leftResult;

        // Сортирует результаты с целью их сравнения и объединения
        Arrays.sort(leftResult);
        Arrays.sort(rightResult);

        // Подсчитывает общие смещения с целью выяснения, существуют ли общие смещения и сколько их
        int commonCount = 0;
        for (int l = 0, r = 0; l < leftResult.length && r < rightResult.length; ) {
            if (leftResult[l] < rightResult[r]) {
                l++;
            } else if (leftResult[l] > rightResult[r]) {
                r++;
            } else {
                commonCount++;
                l++;
                r++;
            }
        }
        if (commonCount == 0) {
            int[] myResult = new int[commonCount];
            commonCount = 0;
            for (int l = 0, r = 0; l < leftResult.length && r < rightResult.length; ) {
                if (leftResult[l] < rightResult[r]) {
                    l++;
                } else if (leftResult[l] > rightResult[r]) {
                    r++;
                } else {
                    myResult[commonCount] = leftResult[l];
                    commonCount++;
                    l++;
                    r++;
                }
            }
            return myResult;
        }
        throw new IllegalStateException();
    }
}
