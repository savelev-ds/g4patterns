package ru.redactor.patterns.grand.behavioral.concurrency.scheduler;

import java.util.ArrayList;

public class Scheduler {

    private Thread runningThread;

    private ArrayList waitingRequests = new ArrayList();
    private ArrayList waitingThreads = new ArrayList();

    public void enter(ScheduleOrdering s) throws InterruptedException {
        Thread thisThread = Thread.currentThread();

        // Когда управляемый ресурс свободен, выполняется синхронизация этого объекта, чтобы
        // два параллельных вывзова метода enter не были выполнены одновременно
        synchronized (this) {
            if (runningThread == null) {
                runningThread = thisThread;
                return;
            }
            waitingThreads.add(thisThread);
            waitingRequests.add(s);
        }

        synchronized (thisThread) {
            while (thisThread != runningThread) {
                thisThread.wait();
            }
        }

        synchronized (this) {
            int i = waitingThreads.indexOf(thisThread);
            waitingThreads.remove(i);
            waitingRequests.remove(i);
        }

    }

    synchronized public void done() {
        if (runningThread != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread");
        }
        int waitCount = waitingThreads.size();
        if (waitCount <= 0) {
            runningThread = null;
        } else if (waitCount == 1) {
            runningThread = (Thread) waitingThreads.get(0);
            waitingThreads.remove(0);
        } else {
            int next = waitCount - 1;
            ScheduleOrdering nextRequest;
            nextRequest = (ScheduleOrdering) waitingRequests.get(next);
            for (int i = waitCount - 2; i >= 0; i--) {
                ScheduleOrdering r;
                r = (ScheduleOrdering) waitingRequests.get(i);
                if (r.scheduleBefore(nextRequest)) {
                    next = i;
                    nextRequest = (ScheduleOrdering) waitingRequests.get(next);
                }
            }
            runningThread = (Thread) waitingThreads.get(next);
            synchronized (runningThread) {
                // так как нет никакого способа быть уверенным в том, что нет никакого другого потока,
                // ожидающего получения права на блокировку объекта runningThread
                runningThread.notifyAll();
            }
        }
    }

}
