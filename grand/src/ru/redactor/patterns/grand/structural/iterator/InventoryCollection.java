package ru.redactor.patterns.grand.structural.iterator;

public class InventoryCollection {

    public InventoryIteratorIF iterator() {
        return new InventoryIterator();
    }

    private class InventoryIterator implements InventoryIteratorIF {

        @Override
        public boolean hasNextInventoryItem() {
            return false;
        }

        @Override
        public InventoryItem getNextInventoryItem() {
            return null;
        }

        @Override
        public boolean hasPrevInventoryItem() {
            return false;
        }

        @Override
        public InventoryItem getPrevInventoryItem() {
            return null;
        }

    }

}
