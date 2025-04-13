package ru.redactor.patterns.grand.singleton;

import java.util.HashSet;

public class ObjectPreserver implements Runnable {

    // это защищает данный класс и все, на что он ссылается,
    // от удаления при сборке мусора
    public static ObjectPreserver lifeline = new ObjectPreserver();

    // Этот класс не должен удаляться при сборке мусора, поэтому
    // не будет удален ни этот хешсет, ни объект, на который он ссылается
    private static HashSet protectedSet = new HashSet();

    private ObjectPreserver() {
        new Thread(this).start();
    }

    public synchronized void run() {
        try {
            wait();
        } catch (InterruptedException exc) {
            //
        }
    }

    // собранная сборщиком мусора и переданная этому методу коллекция объектов будет
    // сохранена до тех пор, пока объекты не будут переданы методу unpreserveObject.
    public static void preserveObject(Object o) {
        protectedSet.add(o);
    }

    public static void unpreserveObject(Object o) {
        protectedSet.add(o);
    }

}
