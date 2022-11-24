package JUC;

import java.util.concurrent.locks.LockSupport;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:02
 * @Description:
 **/
public class CrossPrint1 {
    static Thread thread1, thread2;
    public static void main(String[] args) {
        char[] letters = {'A', 'B','C','D','E','F','G','H'};
        char[] nums = {'1', '2','3','4','5','6','7','8'};

        thread1 = new Thread(() -> {
            for(char c : letters){
                System.out.println(c);
                LockSupport.unpark(thread2);
                LockSupport.park();
            }
        });

        thread2 = new Thread(() -> {
            for(char c : nums){
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(thread1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
