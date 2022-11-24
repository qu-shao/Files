package JUC;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 11:07
 * @Description:
 **/
public class DeadLock {
    public static void main(String[] args) {
        Object resourceA = new Object();
        Object resourceB = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (resourceA){
                System.out.println("ThreadA is waiting for another lock... ...");
                synchronized (resourceB){
                    System.out.println("ThreadA gets two locks!");
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (resourceB){
                System.out.println("ThreadB is waiting for another lock... ...");
                synchronized (resourceA){
                    System.out.println("ThreadB gets two locks!");
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
