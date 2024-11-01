import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBubbleSort {

    public static void parallelBubbleSort(int[] array, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            final int outerIndex = i;
            executor.submit(() -> {
                for (int j = 0; j < n - outerIndex - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        swap(array, j, j + 1);
                    }
                }
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
