import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainParallelSorts {

    private static final int[] SIZES = {10, 100, 1000}; // Different sizes for sample acquisition
    private static final int NUM_THREADS = 4; // Specify the number of threads here

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Comparing Parallel Sorting Algorithms with Varying Array Sizes\n");

        for (int size : SIZES) {
            System.out.println("Array Size: " + size);
            int[] array = generateRandomArray(size);

            // Parallel Merge Sort
            int[] mergeArray = array.clone();
            long startTime = System.nanoTime();
            ParallelMergeSort.parallelMergeSort(mergeArray, NUM_THREADS);
            long endTime = System.nanoTime();
            System.out.println("Parallel Merge Sort Time: " + (endTime - startTime) + " ns");

            // Parallel Bubble Sort
            int[] bubbleArray = array.clone();
            startTime = System.nanoTime();
            ParallelBubbleSort.parallelBubbleSort(bubbleArray, NUM_THREADS);
            endTime = System.nanoTime();
            System.out.println("Parallel Bubble Sort Time: " + (endTime - startTime) + " ns");

            // Parallel Quick Sort
            int[] quickArray = array.clone();
            startTime = System.nanoTime();
            ParallelQuickSort.parallelQuickSort(quickArray, NUM_THREADS);
            endTime = System.nanoTime();
            System.out.println("Parallel Quick Sort Time: " + (endTime - startTime) + " ns");

            // Parallel Selection Sort
            int[] selectionArray = array.clone();
            startTime = System.nanoTime();
            ParallelSelectionSort.parallelSelectionSort(selectionArray, NUM_THREADS);
            endTime = System.nanoTime();
            System.out.println("Parallel Selection Sort Time: " + (endTime - startTime) + " ns");

            System.out.println("--------------------------------------------------\n");
        }
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);  // Random numbers between 0 and 1000
        }
        return array;
    }
}
