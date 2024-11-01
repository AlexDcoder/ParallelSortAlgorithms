import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SerialSorts {

    public static void serialMergeSort(int[] a, int n) {
        if (n < 2) return;
        
        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];
        
        System.arraycopy(a, 0, left, 0, mid);
        System.arraycopy(a, mid, right, 0, n - mid);

        serialMergeSort(left, mid);
        serialMergeSort(right, n - mid);

        merge(a, left, right, mid, n - mid);
    }

    private static void merge(int[] a, int[] left, int[] right, int leftSize, int rightSize) {
        int i = 0, j = 0, k = 0;
        while (i < leftSize && j < rightSize) {
            a[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < leftSize) a[k++] = left[i++];
        while (j < rightSize) a[k++] = right[j++];
    }

    public static void serialBubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void serialQuickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivot = partition(a, high, low);
            serialQuickSort(a, low, pivot - 1);
            serialQuickSort(a, pivot + 1, high);
        }
    }

    private static int partition(int[] a, int high, int low) {
        int pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                i++;
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
            }
        }
        int finalTemp = a[high];
        a[high] = a[i + 1];
        a[i + 1] = finalTemp;
        return i + 1;
    }

    public static void serialSelectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int current_min = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[current_min]) {
                    current_min = j;
                }
            }
            int temp = a[current_min];
            a[current_min] = a[i];
            a[i] = temp;
        }
    }

      // Test each sorting algorithm and measure the time taken
    public static String[] testSortingAlgorithms(int[] array, int qtdTests) {
        int[] mergeSortArray = array.clone();
        int[] bubbleSortArray = array.clone();
        int[] quickSortArray = array.clone();
        int[] selectionSortArray = array.clone();

        long start, end;
        long[] totalMerge = new long[qtdTests], totalBubble = new long[qtdTests], totalSelection = new long[qtdTests], totalQuick = new long[qtdTests];


        for (int i = 0; i < qtdTests; i++) {
            // Merge Sort Timing
            start = System.nanoTime();
            serialMergeSort(mergeSortArray, mergeSortArray.length);
            end = System.nanoTime() - start;
            System.out.println("Merge Sort time (" + array.length + " elements): " + end + " ns");
            totalMerge[i] = end;

            // Bubble Sort Timing
            start = System.nanoTime();
            serialBubbleSort(bubbleSortArray);
            end = System.nanoTime() - start;
            System.out.println("Bubble Sort time (" + array.length + " elements): " + end + " ns");
            totalBubble[i] = end;

            // Quick Sort Timing
            start = System.nanoTime();
            serialQuickSort(quickSortArray, 0, quickSortArray.length - 1);
            end = System.nanoTime() - start;
            System.out.println("Quick Sort time (" + array.length + " elements): " + end + " ns");
            totalQuick[i] = end;

            // Selection Sort Timing
            start = System.nanoTime();
            serialSelectionSort(selectionSortArray);
            end = System.nanoTime() - start;
            System.out.println("Selection Sort time (" + array.length + " elements): " + end + " ns");
            System.out.println("--------------------------------------------------\n");
            totalSelection[i] = end;   
        }
    
        return new String[] {
                    String.format("%d", array.length),
                    String.format("%d", (long) Arrays.stream(totalMerge).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalBubble).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalQuick).average().orElse(0)),
                    String.format("%d", (long) Arrays.stream(totalSelection).average().orElse(0)),
            };
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

    // Main function to initialize arrays of different sizes and test sorting algorithms
    public static void main1(String[] args) {

        int NUMTESTS = 10;

        // Generate random arrays of different sizes
        int[] array10 = new Random().ints(10, 0, 100).toArray();
        int[] array100 = new Random().ints(100, 0, 1000).toArray();
        int[] array1000 = new Random().ints(1000, 0, 10000).toArray();
        int[] array10000 = new Random().ints(10000, 0, 10000).toArray();
        
        String[][] data = new String[4][];

        String PATH = "serial_total.csv";

        System.out.println("Comparing Serial Sorting Algorithms with Varying Array Sizes\n");
        
        // Testing each array with all sorting algorithms
        System.out.println("Testing with array of 10 elements:");
        data[0] = testSortingAlgorithms(array10, NUMTESTS);

        System.out.println("\nTesting with array of 100 elements:");
        data[1] = testSortingAlgorithms(array100, NUMTESTS);

        System.out.println("\nTesting with array of 1000 elements:");
        data[2] = testSortingAlgorithms(array1000, NUMTESTS);
        
        System.out.println("\nTesting with array of 10000 elements:");
        data[3] = testSortingAlgorithms(array10000, NUMTESTS);
        
        try (FileWriter writer = new FileWriter(PATH)) {
            //Writing the line with data
            for (String[] row : data) {
                writeLine(writer, row);
            }
            
        } catch (IOException e) {
            System.out.println("Erro while writing CSV: " + e.getMessage());
        }
    }
}
