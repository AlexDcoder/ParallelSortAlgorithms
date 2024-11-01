import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {

    public static void parallelMergeSort(int[] array, int numThreads) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads);
        forkJoinPool.invoke(new MergeSortTask(array, 0, array.length - 1));
    }

    private static class MergeSortTask extends RecursiveAction {
        private int[] array;
        private int left;
        private int right;

        public MergeSortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                int mid = (left + right) / 2;

                // Fork the tasks
                invokeAll(new MergeSortTask(array, left, mid), new MergeSortTask(array, mid + 1, right));

                // Merge the results
                merge(array, left, mid, right);
            }
        }

        private void merge(int[] array, int left, int mid, int right) {
            int n1 = mid - left + 1;
            int n2 = right - mid;

            int[] leftArray = new int[n1];
            int[] rightArray = new int[n2];

            // Copy data to temporary arrays
            System.arraycopy(array, left, leftArray, 0, n1);
            System.arraycopy(array, mid + 1, rightArray, 0, n2);

            int i = 0, j = 0, k = left;

            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k++] = leftArray[i++];
                } else {
                    array[k++] = rightArray[j++];
                }
            }

            // Copy remaining elements of leftArray
            while (i < n1) {
                array[k++] = leftArray[i++];
            }

            // Copy remaining elements of rightArray
            while (j < n2) {
                array[k++] = rightArray[j++];
            }
        }
    }
}
