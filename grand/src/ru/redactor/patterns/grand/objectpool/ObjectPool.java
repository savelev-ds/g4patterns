package ru.redactor.patterns.grand.objectpool;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;

public class ObjectPool<T> implements ObjectPoolIF<T> {

    private CreationIF<T> creator;
    // реальное количество объектов
    private int size;
    private Object[] pool;
    private int maxInstances;
    private int instanceCount;
    private Object lockObject = new Object();

    public ObjectPool(Class<? extends T> poolClass, CreationIF<T> creator, int capacity, int maxInstances) {
        this.creator = creator;
        this.maxInstances = maxInstances;
        pool = (Object[]) Array.newInstance(poolClass, capacity);
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return pool.length;
    }

    /**
     * @return количество управляемых пулом объектов, существующих в данный момент
     */
    public int getInstanceCount() {
        return instanceCount;
    }

    /**
     * @return максимальное количество управляемых пулом объектов, которым пул разрешает существовать одновременно
     */
    public int getMaxInstances() {
        return maxInstances;
    }

    /**
     * Задаёт максимальное количество объектов, которые могут использоваться в пуле, ожидая повторного использования
     *
     * @param newValue - новое значение для максимальноо количества объектов, которые могут находиться в пуле. Это
     *                 значение должно быть больше нуля
     */
    public void setMaxInstances(int newValue) {
        if (newValue <= 0) {
            throw new IllegalArgumentException();
        }
        synchronized (lockObject) {
            Object[] newPool = new Object[newValue];
            System.arraycopy(pool, 0, newPool, 0, newValue);
            pool = newPool;
        }
    }

    /**
     * Возвращает из пула объект. При пустом пуле будет создан объект, если количество управляемых пулом объектов
     * не больше или равно значению, возвращаемому методом getInstances.
     *
     * Если количество управляемых пулом объектов превышает это значение, то данный метод возвращает null
     */
    public T getObject() {
        synchronized (lockObject) {
            if (size > 0) {
                return removeObject();
            } else if (getInstanceCount() < getMaxInstances()) {
                return createObject();
            }
            return null;
        }
    }

    /**
     * Возвращает из пула объект. При пустом пуле будет создан объект, если количество управляемых пулом объектов
     * не больше или равно максимальному.
     *
     * Если количество управляемых пулом объектов больше этого значения, метод будет ждать до тех пор, пока
     * какой-нибудь управляющий объект не станет доступным для повторного использования.
     */
    public T waitForObject() throws InterruptedException {
        synchronized (lockObject) {
            if (size > 0) {
                return removeObject();
            } else if (getInstanceCount() < getMaxInstances()) {
                return createObject();
            } else {
                do {
                    wait();
                } while (size <= 0);
                return removeObject();
            }
        }
    }

    /**
     * Создает объект, управляемый этим пулом
     */
    private T createObject() {
        T newObject = creator.create();
        pool[size] = newObject;
        size++;
        return newObject;
    }

    private T removeObject(){
        size--;
        return (T) pool[size];
    }

    /**
     * Освобождает объект для пула с целью его повторного использования
     */
    public void release(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        synchronized (lockObject) {
            if (getSize() < getCapacity()) {
                pool[size] = obj;
                size++;
                lockObject.notify();
            }
        }
    }

}
