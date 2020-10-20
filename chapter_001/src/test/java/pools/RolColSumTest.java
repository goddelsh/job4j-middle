package pools;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {10, 11, 12}};

        RolColSum.Sums[] sums = RolColSum.sum(array);

        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(15));
        assertThat(sums[2].getRowSum(), is(33));
        assertThat(sums[2].getColSum(), is(21));
    }

    @Test
    public void sumAsync() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {10, 11, 12}};

        RolColSum.Sums[] sums = RolColSum.asyncSum(array);

        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(15));
        assertThat(sums[2].getRowSum(), is(33));
        assertThat(sums[2].getColSum(), is(21));
    }


    @Test
    public void sumAsyncFull() throws ExecutionException, InterruptedException {
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
    public void sumAsyncThousandItr() throws ExecutionException, InterruptedException {
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