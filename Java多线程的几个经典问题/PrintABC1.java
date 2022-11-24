package JUC;

import java.util.concurrent.locks.LockSupport;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 10:59
 * @Description:
 **/
public class PrintABC1 {
    static Thread threadA, threadB, threadC;
    public static void main(String[] args) {
        threadA = new Thread(() -> {
            for(int i = 0;i < 10;i++){
                System.out.println("ThreadA :  A");
                LockSupport.unpark(threadB);
                LockSupport.park();
            }
        });

        threadB = new Thread(() -> {
            for(int i = 0;i < 10;i++){
                LockSupport.park();
                System.out.println("ThreadB :  B");
                LockSupport.unpark(threadC);
            }
        });

        threadC = new Thread(() -> {
            for(int i = 0;i < 10;i++){
                LockSupport.park();
                System.out.println("ThreadC :  C");
                LockSupport.unpark(threadA);
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
