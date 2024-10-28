package parallel.quick_sort;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;



public class ParallelQuickSort extends RecursiveAction{
    private int[] array;
    private int start;
    private int end;
    private int pivot;
    private static final int THRESHOLD = 2;


    public ParallelQuickSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.pivot = array[end];
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            int i = start - 1;
            for (int j = start; j < end; j++) {
                if (array[j] <= pivot) {
                    i++;
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
            int finalTemp = array[end];
            array[end] = array[i + 1];
            array[i + 1] = finalTemp;
        } else {
            int pivot = partition(array, end, start);
            ParallelQuickSort leftTask = new ParallelQuickSort(array, start, pivot - 1);
            ParallelQuickSort rightTask = new ParallelQuickSort(array, pivot + 1, end);
            invokeAll(leftTask, rightTask); 
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

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = {1, 3, 2, 0};
        ParallelQuickSort task = new ParallelQuickSort(array, 0, array.length);
        pool.invoke(task);
        System.out.println(array);
    }

}