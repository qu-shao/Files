package JUC;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:33
 * @Description:
 **/
public class CrossPrint4 {
    public static void main(String[] args) {
        char[] letters = {'A', 'B','C','D','E','F','G','H'};
        char[] nums = {'1', '2','3','4','5','6','7','8'};
        TransferQueue<Character> queue = new LinkedTransferQueue<>();

        Thread thread1 = new Thread(() -> {
            try {
                for(char c : nums){
                    System.out.println(queue.take());
                    queue.transfer(c);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                for(char c : letters){
                    queue.transfer(c);
                    System.out.println(queue.take());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
    }
}
