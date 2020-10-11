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

    @Ignore
    @Test
    public void checkAtomicRefs() throws InterruptedException {
        CASCount threadSafeCount = new CASCount();

        List<Integer> resultList = new ArrayList<>();

        Thread fisrt = new Thread(
                () -> {
                    int counter = 0;
                    for (int i = 0; i < 10; i++) {
                        counter = threadSafeCount.get();
                        resultList.add(counter);
                        threadSafeCount.increment();
                    }
                });

        Thread second = new Thread(
                () -> {
                    int counter = 0;
                    for (int i = 0; i < 10; i++) {
                        counter = threadSafeCount.get();
                        resultList.add(counter);
                        threadSafeCount.increment();
                    }
                });

        fisrt.start();
        second.start();
        fisrt.join();
        second.join();

        assertThat(resultList, is(IntStream.range(0, 10).boxed().collect(Collectors.toList())));

    }
}