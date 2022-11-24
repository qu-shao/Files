package JUC;

import java.util.concurrent.CountDownLatch;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:15
 * @Description:
 **/
public class CrossPrint2 {
    public static void main(String[] args) {
        char[] letters = {'A', 'B','C','D','E','F','G','H'};
        char[] nums = {'1', '2','3','4','5','6','7','8'};
        Object object = new Object();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread1 = new Thread(() -> {
            synchronized (object){
                countDownLatch.countDown();
                for(char c : letters){
                    System.out.println(c);
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                object.notify();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (object){
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for(char c : nums){
                    System.out.println(c);
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                object.notify();
            }
        });

        thread1.start();
        thread2.start();
    }
}
