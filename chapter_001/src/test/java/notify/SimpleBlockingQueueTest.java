package notify;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void testBlockongQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue  = new SimpleBlockingQueue<>();
        List<Integer> producerList = new ArrayList<>();
        List<Integer> consumerList = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        for (int i = 0; i < 5; i++) {
                            producerList.add(i + 1);
                            queue.offer(i + 1);
                            System.out.println(Thread.currentThread().getName() + " insert " + i);
                            if (i % 3 == 0) {
                                Thread.sleep(100);
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
                        while (count < 5) {
                            count++;
                            int result = queue.poll();
                            consumerList.add(result);
                            System.out.println(Thread.currentThread().getName() + " result = " + result);
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
        assertThat(producerList, is(List.of(1, 2, 3, 4, 5)));
        assertThat(consumerList, is(List.of(1, 2, 3, 4, 5)));
    }
}