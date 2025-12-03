package ru.redactor.patterns.grand.behavioral.concurrency.doublebuffering;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class DoubleBufferedInputStream extends FilterInputStream {

    private static final int DEFAULT_BUFFER_COUNT = 2;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private byte[][] buffers;

    private int activeBuffer = 0;

    /**
     * Количество байтов в каждом буфере.
     */
    private int[] counts;

    /**
     * Индекс следующего символа, который должен быть считан из активного буфера.
     *
     * Справедливо следующее утверждение:
     * 0 <= pos <= counts[activeBuffer]
     *
     * Если pos == counts[activeBuffer], то активный буфер пуст.
     */
    private int pos;

    /**
     * Если исключение генерируется во время опережающего чтения с целью
     * заполнения резервного буфера, этой переменной присваивается объект
     * исключения, чтобы он мог быть повторно сгенерирован позже в потоке,
     * запрашивающем данные, когда этот поток достигнет точки, где было
     * сгенерировано исключение.
     */
    private Throwable exception;

    /**
     * Если эта переменная true, достигнут конец потока
     */
    private boolean exhausted;

    /**
     * Эта переменная становится равной true после того, как весь
     * основной входной поток был считан в буфер.
     */
    private boolean eof;

    /**
     * Этот объект отвечает за асинхронное заполнение резервных буферов.
     */
    private BufferFiller myBufferFiller;

    /**
     * Объект блокировки, который используется для синхронизации потоков,
     * запрашивающих данные, и заполнения буферов.
     */
    private Object lockObject = new Object();

    private void checkClosed() throws IOException {
        if (buffers == null) {
            throw new IOException("Stream closed");
        }
    }

    /**
     * Создаёт объект DoubleBufferedInputStream, который будет читать входные данные
     * из этого входного потока, используя заданный по умолчанию размер буфера и два буфера
     */
    public DoubleBufferedInputStream(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Создает объект DoubleBufferedInputStream, который будет читать входные данные
     * из этого входного потока, используя два буфера заданного размера.
     */
    public DoubleBufferedInputStream(InputStream in, int size) {
        this(in, size, DEFAULT_BUFFER_COUNT);
    }

    /**
     * Создаёт объект DoubleBufferedInputStream, который будет читать входыне данные
     * из этого входного потока, используя заданное количество буферов определённых
     * размеров.
     */
    public DoubleBufferedInputStream(InputStream in, int size, int bufferCount) {
        super(in);
        if (size < 1) {
            String msg = "Buffer size < 1";
            throw new IllegalArgumentException(msg);
        }
        if (bufferCount < 2) {
            bufferCount = 2;
        }

        buffers = new byte[bufferCount][size];
        counts = new int[bufferCount];
        myBufferFiller = new BufferFiller();
    }

    /**
     * Возвращает следующий байт данных или -1, если был достигнут конец потока
     */
    public synchronized int read() throws IOException {
        checkClosed();
        if (eof) {
            return -1;
        }
        if (pos >= counts[activeBuffer]) {
            eof = !advanceBuffer();
        }
        if (eof) {
            return -1;
        }

        // Возвращает результат операции (&)
        // над следующим символом и 0xff, поэтому значения 0х80-0хff больше не
        // рассматриваются, как отрицательные
        int c = buffers[activeBuffer][pos++] & 0xFF;
        if (pos >= counts[activeBuffer]) {
            eof = !advanceBuffer();
        }
        return c;
    }

    /**
     * Читает заданное количество байтов из этого входного потока в определённый байтовый
     * массив, начиная с заданного смещения
     *
     * @return Возвращает количество считанных байтов или -1,
     * если был достигнут конец потока.
     */
    public synchronized int read(byte[] b, int off, int len) throws IOException {
        checkClosed();
        if ((off | len | (off + len) | (b.length - (off + len))) < 0) {
            throw new IndexOutOfBoundsException();
        } else if (eof) {
            return -1;
        } else if (len == 0) {
            return 0;
        }
        int howMany = 0;
        do {
            if (len <= counts[activeBuffer] - pos) {
                System.arraycopy(buffers[activeBuffer], pos, b, off, len);
                howMany += len;
                pos += len;
                len = 0;
            } else {
                int remaining = counts[activeBuffer] - pos;
                System.arraycopy(buffers[activeBuffer], pos, b, off, remaining);
                howMany += remaining;
                pos += remaining;
                len -= remaining;
                off += remaining;
            }
            if (pos >= counts[activeBuffer]) {
                eof = !advanceBuffer();
            }
        } while (!eof && len > 0);
        return howMany;
    }

    /**
     * Этот метод делает текущий активный буфер пустым резервным буфером, а следующий
     * резервный буфер - новым активным буфером. Если следующий резервный буфер еще
     * не был полностью заполнен и есть основания полагать, что он будет заполнен, то
     * этот метод ожидает заполненияю
     *
     * Возвращает true, если этот вызов был успешным при переходе к другому заполненному
     * буферу
     */
    private boolean advanceBuffer() throws IOException {
        int nextActiveBuffer = (activeBuffer + 1) % counts.length;
        if (counts[nextActiveBuffer] == 0) {
            if (exhausted) {
                rethrowException();
                return false;
            }

            // Мы не знаем, существует ли текущий запрос на заполнение резервного буфера
            // или нет, поэтмоу выясняем это
            myBufferFiller.fillReserve();

            synchronized (lockObject) {
                while (counts[nextActiveBuffer] == 0 && !exhausted) {
                    try {
                        lockObject.wait();
                    } catch (InterruptedException e) {
                        IOException ioe = new InterruptedIOException();
                        ioe.initCause(e);
                        throw ioe;
                    }
                }
            }

            if (counts[nextActiveBuffer] == 0 && exhausted) {
                rethrowException();
                return false;
            }
        }

        // Теперь нам известно, что следующий буфер содержит данные,
        // поэтому делаем его активным.
        counts[activeBuffer] = 0;
        activeBuffer = nextActiveBuffer;
        pos = 0;

        // Сейчас прежний активный буфер является пустым резервным буфером, попытаемся
        // его заполнить до того, как он нам понадобится.
        myBufferFiller.fillReserve();

        return true;

    }

    /**
     * В текущем потокое повторно сгенерируем исключение, которое мы перехватили
     * потоком, выполняющим опережающее чтениею
     */
    private void rethrowException() throws IOException {
        if (exception != null) {
            Throwable e = exception;
            exception = null;
            close();
            throw (IOException) e;
        }
    }

    /**
     * Этот метод пропускает заданное количество байтов или, возможно, меньше.
     * Чтобы избежать проблемы, связанной с тем, что этот метод должен ожидать завершения
     * операции опережающего чтения, такая реализация метода не пропустит байтов больше,
     * чем их содержится в данный момент в активном и резервном буферах.
     *
     * @return Возвращает реальное количество пропущенных байтов
     */
    public synchronized long skip(long n) throws IOException {
        checkClosed();
        if (n <= 0) {
            return 0;
        }
        long skipped = 0;
        int thisBuffer = activeBuffer;
        do {
            int remaining = counts[thisBuffer] - pos;
            if (remaining >= n) {
                pos += n;
                skipped += n;
                break;
            }
            pos = 0;
            n -= remaining;
            skipped += remaining;
            thisBuffer = (thisBuffer + 1) % counts.length;
        } while (thisBuffer != activeBuffer);

        activeBuffer = thisBuffer;
        myBufferFiller.fillReserve();
        return skipped;
    }

    /**
     * Возвращает количество байтов, которые могут быть прочитаны из данного
     * входного потока без блокировки.
     *
     * Эта реализация возвращает общее количество байтов данных в активном и
     * резервном буферах
     */
    public synchronized int available() throws IOException {
        checkClosed();
        int count = 0;
        for (int i = 0; i < counts.length; i++) {
            count += counts[i];
        }
        return count;
    }

    /**
     * Закрывает этот входной поток и освобождает все системные ресурсы,
     * связанные с этим потоком.
     */
    public void close() throws IOException {
        if (buffers == null) {
            return;
        }
        myBufferFiller.close();
        buffers = null;
        counts = null;
    }

    /**
     * Этот внутренний класс отвечает за асинхронное заполнение резервных буферов
     */
    private class BufferFiller implements Runnable {

        /**
         * Эта переменная равна true после завершения обращения к fillReserve
         * и остаётся true до тех пор, пока не закончится запрошенное асинхронное
         * заполнение резервных буферов.
         */
        private boolean outstandingFillRequest;

        /**
         * Этот поток отвечает за асинхронное заполнение резервных буферов
         */
        private Thread myThread;

        BufferFiller() {
            myThread = new Thread(this, "Buffer Filler");
            myThread.start();
        }

        /**
         * Синхронное заполнение одного буфера.
         * Полагаем, что все вопросы синхронизации были решены тем, кто вызывает
         * этот метод
         *
         * @param ndx - индекс заполняемого буфера
         * @return - Возвращает количество байтов, считанных в буфер.
         */
        private int fillOneBuffer(int ndx) throws IOException {
            counts[ndx] = in.read(buffers[ndx]);
            return counts[ndx];
        }

        /**
         * Заполняем любые пустые резервные буферы
         */
        private void fill() throws IOException {
            for (
                int i = (activeBuffer + 1 ) % counts.length;
                i != activeBuffer && myThread.isInterrupted();
                i = (i + 1) % counts.length) {
                if (counts[i] == 0) {
                    int thisCount = fillOneBuffer(i);
                    if (thisCount == -1) {
                        exhausted = true;
                        Thread.currentThread().interrupt();
                    } else {
                        // Оповещает любой поток, ожидающий самого последнего  заполненного буфера.
                        synchronized (lockObject) {
                            lockObject.notifyAll();
                        }
                    }
                }
            }
        }

        /**
         * Это общая логика для предварительного заполнения резервных буферов
         */
        public synchronized void run() {
            try {
                while (!myThread.isInterrupted() && !exhausted) {
                    synchronized (this) {
                        while (!outstandingFillRequest) {
                            wait();
                        }
                    }
                    fill();
                    outstandingFillRequest = false;
                }
            } catch (InterruptedException e) {
                // do nothing
            } catch (ThreadDeath e) {
                throw e;
            } catch (Throwable e) {
                exception = e;
            } finally {
                exhausted = true;
                synchronized (lockObject) {
                    lockObject.notifyAll();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    if (exception == null) {
                        exception = e;
                    }
                }
                in = null;
            }
        }

        /**
         * Запрашивает асинхронное выполнение всех резервных буферов.
         * Если операция асинхронного выполнения уже приведена в действие, то вызов этого метода не принесёт
         * никакого эффекта.
         */
        synchronized void fillReserve() {
            outstandingFillRequest = true;
            notify();
        }

        /**
         * Завершает асинхронное заполнение буфера
         */
        void close() {
            myThread.interrupt();
        }

    }

}
