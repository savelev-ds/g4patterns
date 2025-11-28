package ru.redactor.patterns.grand.behavioral.concurrency.readwritelock;

public class Bid {

    private int bid = 0;
    private ReadWriteLock2 lockManager = new ReadWriteLock2();

    public int getBid() throws InterruptedException {
        lockManager.readLock();
        int bid = this.bid;
        lockManager.done();
        return bid;
    }

    public void setBid(int bid) throws InterruptedException {
        lockManager.writeLock();
        if (bid > this.bid) {
            this.bid = bid;
        }
        lockManager.done();
    }

}
