package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        queue.offer(value);
        this.notify();
    }

    public synchronized T poll() throws InterruptedException {
        T result = null;
        while (result == null) {
            result = queue.poll();
            if (result == null) {
                this.wait();
            }
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue  = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        for (int i = 0; i < 10; i++) {
                            queue.offer(i);
                            System.out.println(Thread.currentThread().getName() + " insert " + i);
                            if (i % 3 == 0) {
                                Thread.sleep(3000);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                },
                "Master"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        int count = 0;
                        while (count < 10) {
                            count++;
                            System.out.println(Thread.currentThread().getName() + " result = " + queue.poll());
                        }
                        System.out.println(Thread.currentThread().getName() + " waiting finished");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                },
                "Slave"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
