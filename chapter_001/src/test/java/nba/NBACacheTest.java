package nba;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NBACacheTest {

    @Test
    public void add() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        NBACache cache = new NBACache();
        Base first = new Base(1, 1, "First");

        cache.add(new Base(1, 1, "First"));

        Thread firstThread = new Thread(
                () -> {
                    try {
                        cache.add(new Base(1, 2, "First_new"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        firstThread.start();
        firstThread.join();

        assertThat(ex.get().toString(), is("OptimisticException"));

    }

    @Test
    public void update() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        NBACache cache = new NBACache();
        Base first = new Base(1, 1, "First");
        Base second = new Base(2, 1, "Second");

        cache.add(first);
        cache.add(second);

        Thread firstThread = new Thread(
                () -> {
                    try {
                        cache.update(new Base(1, 1, "First_new"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        Thread secondThread = new Thread(
                () -> {
                    try {
                        cache.update(new Base(2, 1, "Second_new"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();

        first.setName("Excp first");
        second.setName("Excp secod");

        assertThat(cache.get(new Base(1, 0, null)).getName(), is("First_new"));
        assertThat(cache.get(new Base(2, 0, null)).getName(), is("Second_new"));
    }

    @Test
    public void delete() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        NBACache cache = new NBACache();
        Base first = new Base(1, 1, "First");
        Base second = new Base(2, 1, "Second");

        cache.add(first);
        cache.add(second);

        Thread firstThread = new Thread(
                () -> {
                    try {
                        cache.update(new Base(1, 1, "First_new"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        Thread secondThread = new Thread(
                () -> {
                    try {
                        cache.delete(new Base(1, 1, "Second_new"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();

        first.setName("Excp first");
        second.setName("Excp secod");

        assertThat(cache.get(new Base(1, 0, null)) == null, is(true));
        assertThat(ex.get() == null, is(true));
    }
}