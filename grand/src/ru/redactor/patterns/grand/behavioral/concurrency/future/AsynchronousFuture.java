package ru.redactor.patterns.grand.behavioral.concurrency.future;

public class AsynchronousFuture {

    private Object result;
    private boolean resultIsSet;
    private Exception problem;

    /**
     * Возвращает true, если результат был получен
     */
    public boolean checkResult() {
        return resultIsSet;
    }

    /**
     * Возвращает объект Result для этого потока Future.
     * Если он еще не получен, ждёт, пока он не будет получен
     */
    public synchronized Object getResult() throws Exception {
        while (!resultIsSet) {
            wait();
        }
        if (problem != null) {
            throw problem;
        }
        return result;
    }

    /**
     * Обращаемся к этому методу для задания результата вычисления.
     * Этот метод должен вызываться только один раз.
     */
    public synchronized void setResult(Object result) {
        if (this.resultIsSet) {
            String msg = "Result is already set";
            throw new IllegalStateException(msg);
        }
        this.result = result;
        resultIsSet = true;
        notifyAll();
    }

    /**
     * Если асинхронное вычисление, связанное с этим объектом, генерирует исключение, передаем исключение этому
     * методу, и исключение будет снова сгенерировано методом getResult
     */
    public synchronized void setException(Exception e) {
        problem = e;
        resultIsSet = true;
        notifyAll();
    }

}
