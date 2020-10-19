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


    @Test
    public void test() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3}
        };

        RolColSum.Sums[] sums = RolColSum.sum(array);

        assertThat(sums[0].getRowSum(), is(3));
        assertThat(sums[0].getColSum(), is(6));
        assertThat(sums[1].getRowSum(), is(6));
        assertThat(sums[1].getColSum(), is(6));
        assertThat(sums[2].getRowSum(), is(9));
        assertThat(sums[2].getColSum(), is(6));
    }


    @Test
    public void test2() throws ExecutionException, InterruptedException {
        int n = 1000;
        int[][] data = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = 1;
            }
        }
        RolColSum.Sums[] result = RolColSum.asyncSum(data);
        for (int i = 0; i < n; i++) {
            if (result[i].getColSum() != n || result[i].getRowSum() != n) {
                throw new RuntimeException("Wrong solution");
            }
        }
    }


}