import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainParallelSorts {

    private static final int[] SIZES = { 10, 100, 1000, 10000 }; // Different sizes for sample acquisition
    private static final int NUM_THREADS = 4; // Specify the number of threads here
    private static final String PATH = "parallel_total.csv";
    private static final int NUMTESTS = 10;

    public static void main1(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Comparing Parallel Sorting Algorithms with Varying Array Sizes\n");

        long[] totalMerge = new long[NUMTESTS], totalBubble = new long[NUMTESTS], totalSelection = new long[NUMTESTS], totalQuick = new long[NUMTESTS];

        String[][] data = new String[SIZES.length][];

        for (int size : SIZES) {
            System.out.println("Array Size: " + size);
            for (int i = 0; i < NUMTESTS; i++) {
                int[] array = generateRandomArray(size);
                // Parallel Merge Sort
                int[] mergeArray = array.clone();
                long startTime = System.nanoTime();
                ParallelMergeSort.parallelMergeSort(mergeArray, NUM_THREADS);
                long endTime = System.nanoTime();
                System.out.println("Parallel Merge Sort Time: " + (endTime - startTime) + " ns");
                totalMerge[i] = endTime - startTime;
    
                // Parallel Bubble Sort
                int[] bubbleArray = array.clone();
                startTime = System.nanoTime();
                ParallelBubbleSort.parallelBubbleSort(bubbleArray, NUM_THREADS);
                endTime = System.nanoTime();
                System.out.println("Parallel Bubble Sort Time: " + (endTime - startTime) + " ns");
                totalBubble[i] = endTime - startTime;
    
                // Parallel Quick Sort
                int[] quickArray = array.clone();
                startTime = System.nanoTime();
                ParallelQuickSort.parallelQuickSort(quickArray, NUM_THREADS);
                endTime = System.nanoTime();
                System.out.println("Parallel Quick Sort Time: " + (endTime - startTime) + " ns");
                totalQuick[i] = endTime - startTime;
    
                // Parallel Selection Sort
                int[] selectionArray = array.clone();
                startTime = System.nanoTime();
                ParallelSelectionSort.parallelSelectionSort(selectionArray, NUM_THREADS);
                endTime = System.nanoTime();
                System.out.println("Parallel Selection Sort Time: " + (endTime - startTime) + " ns");
                totalSelection[i] = endTime - startTime;
                
                System.out.println("--------------------------------------------------\n");
            }

            int index = findIndex(SIZES, size);
                data[index] = new String[] {
                    String.format("%d", size),
                    String.format("%d", (long) Arrays.stream(totalMerge).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalBubble).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalQuick).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalSelection).average().orElse(0)),
            };
            
        }
        
        try (FileWriter writer = new FileWriter(PATH)) {
            //Writing the line with data
            for (String[] row : data) {
                writeLine(writer, row);
            }
            
        } catch (IOException e) {
            System.out.println("Erro while writing CSV: " + e.getMessage());
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

    private static int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private static void writeLine(FileWriter writer, String[] values) throws IOException {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            line.append(values[i]);
            if (i < values.length - 1) {
                line.append(",");
            }
        }
        line.append("\n");
        writer.write(line.toString());
    }
    
}
