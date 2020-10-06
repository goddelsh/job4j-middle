package notify;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() throws InterruptedException {
        count++;
        if (count == total) {
            this.await();
        }
    }

    public synchronized void await() throws InterruptedException {
        if (total != count) {
            this.wait();
        } else {
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(2);
        Thread master = new Thread(
                () -> {
                    try {
                        barrier.count();
                        System.out.println(Thread.currentThread().getName() + " started");
                        System.out.println(Thread.currentThread().getName() + " working...");
                        Thread.sleep(3000);
                        barrier.count();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        barrier.await();
                        System.out.println(Thread.currentThread().getName() + " waiting finished");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}