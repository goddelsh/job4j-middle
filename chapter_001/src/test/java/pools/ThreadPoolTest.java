package pools;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void work() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        List<String> results = new ArrayList<>();

        pool.work(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            results.add("First");
        });
        pool.work(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            results.add("Second");
        });
        pool.work(() -> results.add("Third"));

        Thread.sleep(1000);

        assertThat(results.size(), is(3));

    }

    @Test
    public void shutdown() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        List<String> results = new ArrayList<>();

       // Thread.sleep(3000);
       pool.work(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            results.add("Second");
        });
        pool.work(() -> results.add("Third"));

        pool.shutdown();

        assertThat(pool.isThreasStoped(), is(true));
    }
}