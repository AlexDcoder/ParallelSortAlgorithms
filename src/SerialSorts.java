public class SerialSorts {
    public static void serialMergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];
        for (int i = 0; i < mid; i++) {
            left[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            right[i - mid] = a[i];
        }

        serialMergeSort(left, mid);
        serialMergeSort(right, n - mid);

        merge(a, left, right, mid, n - mid);
    }

    private static void merge(int[] a, int[] left, int[] right, int leftSize, int rightSize) {
        int i = 0, j = 0, k = 0;
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
            }
        }

        while (i < leftSize) {
            a[k++] = left[i++];
        }

        while (j < rightSize) {
            a[k++] = right[j++];
        }
    }

    public static void serialBubbleSort(int[] a) {
        int n = a.length;
        boolean swapped;
        for (int i = 0; i < n; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
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
    public static void main(String[] args) {
        int[] example = {10, 9, 2, 11, 5};
        for (int i : example) {
            System.out.print(i + " ");
        }
        System.out.println();
        long start = System.nanoTime();
        //serialMergeSort(example, example.length);
        //serialBubbleSort(example);
        //serialQuickSort(example, 0, example.length - 1);
        serialSelectionSort(example);
        long total = System.nanoTime() - start;

        for (int i : example) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("TEMPO TOTAL: " + total);
    }
}
