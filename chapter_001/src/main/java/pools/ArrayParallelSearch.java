package pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ArrayParallelSearch<T> {
    final private ForkJoinPool forkJoinPool = new ForkJoinPool();

    public List<Integer> search(List<T> array, T value) {
        return forkJoinPool.invoke(new SortTask(array, 0, value));
    }

    private class SortTask extends RecursiveTask<List<Integer>> {

        private List<T> array;
        private int index;
        private T value;

        public SortTask(List<T> array, int index, T value) {
            this.array = array;
            this.index = index;
            this.value = value;
        }

        @Override
        protected List<Integer> compute() {
            List<Integer> result = new ArrayList<>();
            if (array.size() > 10) {
                int sizeCount = array.size() / 2;
                List<T> left = array.subList(0, sizeCount);
                List<T> right = array.subList(sizeCount + 1, array.size());
                SortTask leftTask = new SortTask(left, 0, value);
                SortTask rightTask = new SortTask(right, sizeCount + 1, value);

                rightTask.invoke();
                leftTask.invoke();

                result.addAll(rightTask.join());
                result.addAll(leftTask.join());
            } else {
                result = search(array, index, value);
            }
            return result;
        }

        private List<Integer> search(List<T>  array, int index, T value) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).equals(value)) {
                    result.add(index);
                }
                index++;
            }
            return result;
        }
    }

}
