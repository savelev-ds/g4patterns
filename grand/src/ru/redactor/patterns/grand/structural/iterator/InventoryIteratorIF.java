package ru.redactor.patterns.grand.structural.iterator;

public interface InventoryIteratorIF {

    boolean hasNextInventoryItem();
    InventoryItem getNextInventoryItem();
    boolean hasPrevInventoryItem();
    InventoryItem getPrevInventoryItem();

}
