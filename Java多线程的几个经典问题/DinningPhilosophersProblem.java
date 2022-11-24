package JUC;

/**
 * @User: Qushao
 * @DateTime: 2022/11/24 11:16
 * @Description:
 **/
public class DinningPhilosophersProblem {
    public static void main(String[] args) {
        Chopsticks chopsticks1 = new Chopsticks(1);
        Chopsticks chopsticks2 = new Chopsticks(2);
        Chopsticks chopsticks3 = new Chopsticks(3);
        Chopsticks chopsticks4 = new Chopsticks(4);
        Chopsticks chopsticks5 = new Chopsticks(5);

        Philosohper philosohper1 = new Philosohper("Philosohper1", 1, chopsticks1, chopsticks5);
        Philosohper philosohper2 = new Philosohper("Philosohper2", 2, chopsticks2, chopsticks1);
        Philosohper philosohper3 = new Philosohper("Philosohper3", 3, chopsticks3, chopsticks2);
        Philosohper philosohper4 = new Philosohper("Philosohper4", 4, chopsticks4, chopsticks3);
        Philosohper philosohper5 = new Philosohper("Philosohper5", 5, chopsticks5, chopsticks4);

        philosohper1.start();
        philosohper2.start();
        philosohper3.start();
        philosohper4.start();
        philosohper5.start();
    }
}

class Chopsticks{
    private int id;
    public Chopsticks(int id){
        this.id = id;
    }
}

class Philosohper extends Thread{
    private Chopsticks left;
    private Chopsticks right;
    private int index;
    private String name;

    public Philosohper(String name, int index, Chopsticks left, Chopsticks right){
        this.name = name;
        this.index = index;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        if(index % 2 == 0){
            synchronized (left){
                System.out.println(this.name + " is waiting for the right chopsticks... ...");
                synchronized (right){
                    System.out.println(this.name + " finishes his dinner!");
                }
            }
        }else{
            synchronized (right){
                System.out.println(this.name + " is waiting for the left chopsticks... ...");
                synchronized (left){
                    System.out.println(this.name + " finishes his dinner!");
                }
            }
        }
    }
}
