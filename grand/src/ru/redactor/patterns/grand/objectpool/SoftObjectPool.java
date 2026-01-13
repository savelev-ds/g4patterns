package ru.redactor.patterns.grand.objectpool;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class SoftObjectPool<T> implements ObjectPoolIF<T> {

    private final ArrayList<SoftReference<T>> pool;

    private CreationIF<T> creator;

    private int instanceCount;
    private int maxInstances;


    public SoftObjectPool(CreationIF<T> creator) {
        this(creator, Integer.MAX_VALUE);
    }

    /**
     * @param creator - объект, кооторому пул будет делегировать ответственность за создание управляемых им объектов
     * @param maxInstances - максимальное количество экземпляров класса, которым пул разрешает существовать
     *                     одновременно. Если пулу поступает запрос на создание экземпляра, когда в пуле нет объектов,
     *                     ожидающих повторного использования, и имеется множество таких управляемых пулом объектов,
     *                     которые используются в данный момент, то пул не будет создавать объект до тех пор, пока
     *                     в пул не будет возвращен объект для повторного использования
     */
    public SoftObjectPool(CreationIF<T> creator, int maxInstances) {
        this.creator = creator;
        this.maxInstances = maxInstances;
        this.pool = new ArrayList<>();
    }

    /**
     * @return количество объектов в пуле, ожидающих повторного использования. Реальное количество может быть меньше
     * этого значения, поскольку возвращаемая величина - количество мягких ссылок в пуле. Некоторые или все такие
     * мягкие ссылки могут быть очищены сборщиком мусора
     */
    public int getSize() {
        synchronized (pool) {
            return pool.size();
        }
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
     * Задает максимальное количество управляемых пулом объектов, которым этот пул разрешает существовать одновременно
     *
     * Если к пулу поступает запрос на создание экземпляра класса, когда в пуле нет объектов, ожидающих повторного
     * использования, и имеется множество таких управляемых пулом объектов, которые используются в данный момент, то
     * пул не будет создавать объект до тех пор, пока в пул не будет возвращен объект для повторного использования
     *
     * @param maxInstances новое значение для максимального количества управляемых пулом объектов, которые могут
     *                     существовать одновременно. Установка в этой переменной значения, меньшего getInstanceCount,
     *                     не повлечет за собой удаление объектов из пула. Просто предотвращается создание каких-либо
     *                     новых объектов, управляемых пулом
     */
    public void setMaxInstances(int maxInstances) {
        this.maxInstances = maxInstances;
    }

    /**
     * Возвращает из пула объект. При пустом пуле будет создан объект, если количество управлемых пулом объектов не
     * больше или равно максимальному количеству объектов. Если количество управляемых пулом объектов превышает
     * значение, то данный метод вернёт null
     */
    public T getObject() {
        synchronized (pool) {
            T thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            }
        }
        if (getInstanceCount() < getMaxInstances()) {
            return createObject();
        }
        return null;
    }

    /**
     * Возвращает из пула объект. При пустом пуле будет создан объект, если количество управляемых пулом объектов
     * не больше или равно максимальному значению. Если количество управляемых пулом объектов превышает это значение,
     * то данный метод будет ждать до тех пор, пока какой-нибудь объект не станет доступным для повторного использования
     */
    public T waitForObject() throws InterruptedException {
        synchronized (pool) {
            T thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            }
            if (getInstanceCount() < getMaxInstances()) {
                return createObject();
            } else {
                do {
                    // ожидать извещения о том, что объект был возвращен в пул
                    pool.wait();
                    thisObject = removeObject();
                } while (thisObject == null);
            }
            return thisObject;
        }
    }

    /**
     * Удаляет объект из коллекции пула и возвращает его
     */
    private T removeObject() {
        while (pool.size() > 0) {
            SoftReference<T> thisRef = pool.remove(pool.size() - 1);
            T thisObject = thisRef.get();
            if (thisObject != null) {
                return thisObject;
            }
            instanceCount--;
        }
        return null;
    }

    /**
     * Создает объект, управляемый этим пулом
     */
    private T createObject() {
        T newObject = creator.create();
        pool.add(new SoftReference<>(newObject));
        instanceCount++;
        return newObject;
    }

    /**
     * Освобождает объект, помещая его в пул для повторного использования
     */

    public void release(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        synchronized (pool) {
            pool.add(new SoftReference<>(obj));
            pool.notify();
        }
    }

}
