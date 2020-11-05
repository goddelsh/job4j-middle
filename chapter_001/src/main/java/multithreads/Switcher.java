package multithreads;

public class Switcher {
    public static void main(String[] args) throws InterruptedException {

        MasterSlaveBarrier masterSlaveBarrier = new MasterSlaveBarrier();

        Thread first = new Thread(() -> {
            while (true) {
                try {
                    masterSlaveBarrier.tryMaster();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread A");
                masterSlaveBarrier.doneMaster();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread second = new Thread(() -> {
            while (true) {
                try {
                    masterSlaveBarrier.trySlave();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread B");
                masterSlaveBarrier.doneSlave();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        first.start();
        second.start();
        first.join();
        second.join();
    }
}