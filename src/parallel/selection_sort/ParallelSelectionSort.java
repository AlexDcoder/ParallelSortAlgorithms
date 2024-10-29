import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSelectionSort {

    public static void parallelSelectionSort(int[] array, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            final int outerIndex = i;
            executor.submit(() -> {
                int currentMinIndex = outerIndex;
                for (int j = outerIndex + 1; j < n; j++) {
                    if (array[j] < array[currentMinIndex]) {
                        currentMinIndex = j;
                    }
                }
                swap(array, outerIndex, currentMinIndex);
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {} // Wait for all tasks to finish
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
