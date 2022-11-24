package JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:25
 * @Description:
 **/
public class CrossPrint3 {
    public static void main(String[] args) {
        char[] letters = {'A', 'B','C','D','E','F','G','H'};
        char[] nums = {'1', '2','3','4','5','6','7','8'};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(() -> {
           lock.lock();
            try {
                countDownLatch.countDown();
                for(char c : letters){
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }
                condition.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                countDownLatch.await();
                for(char c : nums){
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }
                condition.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();
    }
}
