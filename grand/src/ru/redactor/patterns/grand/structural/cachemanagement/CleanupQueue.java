package ru.redactor.patterns.grand.structural.cachemanagement;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Этот класс инкапсулирует ReferenceQueue.
 * Он гарантирует, что объекты Reference только одного вида поставлены в очередь в объект ReferenceQueue - это объекты,
 * позволяющие объектам CleanupIF, поставленным в очередь в объект ReferenceQueue, удалять самих себя из любой
 * структуры данных, частью которой они являются
 */
public class CleanupQueue {

    private ReferenceQueue referenceQueue = new ReferenceQueue();

    private boolean cleaning;

    /**
     * Возвращает SoftReference, предназначенный для помещения в очередь ссылок, инкапсулированную в этом объекте.
     * @param obj Объект, на который будет указывать ссылка
     * @param cleanup Объект CleanupIF, метод extricate которого должен вызываться после того, как SoftReference
     *                помещён в очередь
     */
    public SoftReference createSoftReference(Object obj, CleanupIF cleanup) {
        return new SoftCleanupReference(obj, referenceQueue, cleanup);
    }

    public void  cleanup() {
        synchronized (this) {
            if (cleaning) {
                return;
            }
            cleaning = true;
        }
        try {
            while (referenceQueue.poll() != null) {
                SoftCleanupReference r;
                r = (SoftCleanupReference) referenceQueue.remove();
                r.extricate();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleaning = false;
        }
    }

}
