package ru.redactor.patterns.grand.behavioral.concurrency.twophasetermination;

import java.net.Socket;

public class Session implements Runnable {

    private Thread myThread;
    private Portfolio portfolio;
    private Socket mySocket;

    public Session(Socket s) {
        myThread = new Thread(this);
        mySocket = s;
    }

    public void run() {
        initialize();
        while (!myThread.isInterrupted()) {
            portfolio.sendTransactionsToClient(mySocket);
        }
        shutdown();
    }

    /**
     * Запрос на завершение этого сеанса.
     */
    public void interrupt() {
        myThread.interrupt();
    }

    private void initialize() {}

    private void shutdown() {}

}
