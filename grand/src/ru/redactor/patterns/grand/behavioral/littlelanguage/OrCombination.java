package ru.redactor.patterns.grand.behavioral.littlelanguage;

public class OrCombination extends Combination {

    private Combination leftChild, rightChild;

    OrCombination(Combination leftChild, Combination rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    int[] contains(String s) {
        int[] leftResult = leftChild.contains(s);
        int[] rightResult = rightChild.contains(s);
        if (leftResult == null) {
            return rightResult;
        }
        if (rightResult == null) {
            return leftResult;
        }
        if (leftResult.length == 0) {
            return leftResult;
        }
        if (rightResult.length == 0) {
            return rightResult;
        }
        // создает массив объединённых результатов
        int[] myResult = new int[leftResult.length + rightResult.length];
        System.arraycopy(leftResult, 0, myResult, 0, leftResult.length);
        System.arraycopy(rightResult, 0, myResult, leftResult.length, rightResult.length);
        return myResult;
    }

}
