package ru.redactor.patterns.grand.objectpool;

public interface ObjectPoolIF<T> {

    void setMaxInstances(int maxInstances);

    T getObject();

    T waitForObject() throws InterruptedException;

    void release(T obj);

}
