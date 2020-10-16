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
}