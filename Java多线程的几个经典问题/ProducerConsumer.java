package JUC;

import java.util.LinkedList;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 11:55
 * @Description:
 **/
public class ProducerConsumer {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Thread p1 = new Thread(new Producer(storage));
        Thread p2 = new Thread(new Producer(storage));
        Thread p3 = new Thread(new Producer(storage));

        Thread c1 = new Thread(new Consumer(storage));
        Thread c2 = new Thread(new Consumer(storage));
        Thread c3 = new Thread(new Consumer(storage));

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

class Producer extends Thread{
    private Storage storage;
    public Producer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run() {
        while(true){
            storage.produce();
        }
    }
}

class Consumer extends Thread{
    private Storage storage;
    public Consumer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run() {
        while(true){
            storage.consume();
        }
    }
}

class Storage{
    private final int MAX_SIZE = 10;
    private LinkedList<Object> list = new LinkedList<>();

    public void produce(){
        synchronized (list){
            while(list.size() + 1 > MAX_SIZE){
                System.out.println("The storage is full!");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.add(new Object());
            System.out.println(Thread.currentThread().getName() + " produces an object!");
            list.notifyAll();
        }
    }

    public void consume(){
        synchronized (list){
            while(list.size() == 0){
                System.out.println("The storage is empty!");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.remove();
            System.out.println(Thread.currentThread().getName() + " consumes an object!");
            list.notifyAll();
        }
    }
}
