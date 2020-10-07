package notify;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        int flag = 3;
        final Thread consumer = new Thread(
                () -> {
                    int count = 0;
                    while (count < flag - 1) {
                        try {
                            count = queue.poll();
                            System.out.println(count);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index < flag; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }
}