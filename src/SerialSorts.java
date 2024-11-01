import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SerialSorts {

    public static void serialMergeSort(int[] a, int n) {
        if (n < 2)
            return;

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
        while (i < leftSize)
            a[k++] = left[i++];
        while (j < rightSize)
            a[k++] = right[j++];
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
            if (!swapped)
                break;
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

    public static long[] testSortingAlgorithms(int[] array) {
        int[] mergeSortArray = array.clone();
        int[] bubbleSortArray = array.clone();
        int[] quickSortArray = array.clone();
        int[] selectionSortArray = array.clone();

        long start, end;
        long[] times = new long[4];

        // Merge Sort Timing
        start = System.nanoTime();
        serialMergeSort(mergeSortArray, mergeSortArray.length);
        end = System.nanoTime() - start;
        times[0] = end;

        // Bubble Sort Timing
        start = System.nanoTime();
        serialBubbleSort(bubbleSortArray);
        end = System.nanoTime() - start;
        times[1] = end;

        // Quick Sort Timing
        start = System.nanoTime();
        serialQuickSort(quickSortArray, 0, quickSortArray.length - 1);
        end = System.nanoTime() - start;
        times[2] = end;

        // Selection Sort Timing
        start = System.nanoTime();
        serialSelectionSort(selectionSortArray);
        end = System.nanoTime() - start;
        times[3] = end;

        return times;
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

    // Main function to initialize arrays of different sizes and test sorting
    // algorithms
    public static void main(String[] args) {
        int NUMTESTS = 10;
        String PATH = "serial_total.csv";

        // Accumulators for averages
        long[] totalTimes = new long[4]; // [merge, bubble, quick, selection]
        int[] sizes = { 10, 100, 1000, 10000 };

        for (int size : sizes) {
            for (int i = 0; i < NUMTESTS; i++) {
                // Generate a random array of specified size
                int[] array = new Random().ints(size, 0, 10000).toArray();

                // Testing each array with all sorting algorithms
                long[] times = testSortingAlgorithms(array);

                // Accumulate total times
                for (int j = 0; j < totalTimes.length; j++) {
                    totalTimes[j] += times[j];
                }
            }

            // Calculate and write averages
            String[] results = new String[5];
            results[0] = String.valueOf(size);
            for (int j = 0; j < totalTimes.length; j++) {
                results[j + 1] = String.valueOf(totalTimes[j] / NUMTESTS);
            }

            // Write the averages to the CSV
            try (FileWriter writer = new FileWriter(PATH, true)) {
                writeLine(writer, results);
            } catch (IOException e) {
                System.out.println("Error while writing CSV: " + e.getMessage());
            }

            // Reset the total times for the next size
            Arrays.fill(totalTimes, 0);
        }
    }
}
