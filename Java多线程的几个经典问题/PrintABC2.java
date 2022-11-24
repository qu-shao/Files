package JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:39
 * @Description:
 **/
public class PrintABC2 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        Thread threadA = new Thread(() -> {
            lock.lock();
            try {
                for(int i = 0;i < 10;i++){
                    System.out.println("ThreadA :  A");
                    if(i == 0)countDownLatch1.countDown();
                    conditionB.signal();
                    conditionA.await();
                }
                conditionB.signal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                countDownLatch1.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            lock.lock();
            try {
                for(int i = 0;i < 10;i++){
                    System.out.println("ThreadB :  B");
                    if(i == 0)countDownLatch2.countDown();
                    conditionC.signal();
                    conditionB.await();
                }
                conditionC.signal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                countDownLatch2.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            lock.lock();
            try {
                for(int i = 0;i < 10;i++){
                    System.out.println("ThreadC :  C");
                    conditionA.signal();
                    conditionC.await();
                }
                conditionA.signal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
