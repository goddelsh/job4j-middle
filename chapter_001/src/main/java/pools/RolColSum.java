package pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;

        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (result[i] == null) {
                    result[i] = new Sums();
                }
                if (result[j] == null) {
                    result[j] = new Sums();
                }
                result[i].setRowSum(result[i].getRowSum() + matrix[i][j]);
                result[j].setColSum(result[j].getColSum() + matrix[i][j]);
            }
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        List<CompletableFuture<Void>> results = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            results.add(computeAndSet(result, matrix, i));
        }
        CompletableFuture<Void> compAll = CompletableFuture.allOf(results.toArray(new CompletableFuture[0]));
        compAll.get();
        return result;
    }

    public static CompletableFuture<Void> computeAndSet(Sums[] result, int[][] matrix,  int index) {
        return CompletableFuture.runAsync(() -> {
            for (int j = 0; j < matrix.length; j++) {
                if (result[index] == null) {
                    result[index] = new Sums();
                }
                if (result[j] == null) {
                    result[j] = new Sums();
                }
                result[index].setRowSum(result[index].getRowSum() + matrix[index][j]);
                result[j].setColSum(result[j].getColSum() + matrix[index][j]);
            }
        });
    }

}