package pools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ArrayParallelSearchTest {

    @Test
    public void testSort() {
        ArrayParallelSearch<String> searchObject = new ArrayParallelSearch<>();

        List<Integer> result = searchObject.search(List.of("a", "b", "c", "a", "d"), "a");

        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(0));
        assertThat(result.get(1), is(3));
    }

    @Test
    public void testSortBigArray() {
        ArrayParallelSearch<String> searchObject = new ArrayParallelSearch<>();

        List<String> accList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            accList.addAll(List.of("a", "b", "c", "a", "d"));
        }

        List<Integer> result = searchObject.search(accList, "a");
        result.sort(Comparator.naturalOrder());

        assertThat(result.size(), is(20000));
        assertThat(result.get(0), is(0));
        assertThat(result.get(1), is(3));
    }

}