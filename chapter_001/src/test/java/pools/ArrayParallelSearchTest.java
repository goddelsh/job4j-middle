package pools;

import org.junit.Test;

import java.util.List;

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

}