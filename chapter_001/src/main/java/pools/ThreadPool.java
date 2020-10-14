package pools;

import notify.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;


public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    private final int size = Runtime.getRuntime().availableProcessors();

    private volatile boolean startFlag = true;

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        while (startFlag) {
                            try {
                                Runnable job = tasks.poll();
                                if (job != null) {
                                    job.run();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, "Pool Thread " + i);
            this.threads.add(thread);
            thread.start();
        }

    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        this.startFlag = false;

        while (!isThreasStoped()) {
            tasks.offer(() -> {
                return;
            });
            System.out.print("\rWaiting threads finished");
        }
        System.out.print("\rThreads finished");
    }

    public boolean isThreasStoped() {
        boolean result = true;
        for (int i = 0; i < this.threads.size(); i++) {
            if (threads.get(i).isAlive()) {
                result = false;
                break;
            }
        }
        return result;
    }
}