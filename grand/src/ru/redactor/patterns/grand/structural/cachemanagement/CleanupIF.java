package ru.redactor.patterns.grand.structural.cachemanagement;

public interface CleanupIF {

    /**
     * При вызове этого метода предполагаем, что оьбъект, который его реализует, удаляет сам себя
     * из любой структуры данных, частью которой он является
     */
    void extricate();

}
