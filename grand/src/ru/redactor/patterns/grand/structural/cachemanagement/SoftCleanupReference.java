package ru.redactor.patterns.grand.structural.cachemanagement;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftCleanupReference extends SoftReference {

    public SoftCleanupReference(Object obj, ReferenceQueue referenceQueue, CleanupIF cleanup) {
        super(obj);
    }


    public void extricate() {

    }
}
