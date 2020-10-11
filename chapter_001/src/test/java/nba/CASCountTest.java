package nba;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void checkAtomicRefs() throws InterruptedException {
        CASCount threadSafeCount = new CASCount();

        Thread fisrt = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        threadSafeCount.increment();
                    }
                });

        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        threadSafeCount.increment();
                    }
                });

        fisrt.start();
        second.start();
        fisrt.join();
        second.join();

        assertThat(threadSafeCount.get(), is(20));

    }
}