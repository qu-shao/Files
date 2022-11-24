package JUC;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 11:55
 * @Description:
 **/
public class ProducerConsumer2 {
    public static void main(String[] args) {
        Storage2 storage = new Storage2();
        Thread p1 = new Thread(new Producer2(storage));
        Thread p2 = new Thread(new Producer2(storage));
        Thread p3 = new Thread(new Producer2(storage));

        Thread c1 = new Thread(new Consumer2(storage));
        Thread c2 = new Thread(new Consumer2(storage));
        Thread c3 = new Thread(new Consumer2(storage));

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

class Producer2 extends Thread {
    private final Storage2 storage;

    public Producer2(Storage2 storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            storage.produce();
        }
    }
}

class Consumer2 extends Thread {
    private final Storage2 storage;

    public Consumer2(Storage2 storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            storage.consume();
        }
    }
}

class Storage2 {
    private final int MAX_SIZE = 10;
    private final LinkedList<Object> list = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition conditionConsumer = lock.newCondition();
    private final Condition conditionProducer = lock.newCondition();

    public void produce() {
        lock.lock();
        try {
            while (list.size() + 1 > MAX_SIZE) {
                System.out.println("The storage is full!");
                try {
                    conditionProducer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.add(new Object());
            System.out.println(Thread.currentThread().getName() + " produces an object!");
            conditionConsumer.signalAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (list.size() == 0) {
                System.out.println("The storage is empty!");
                try {
                    conditionConsumer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.remove();
            System.out.println(Thread.currentThread().getName() + " consumes an object!");
            conditionProducer.signalAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
