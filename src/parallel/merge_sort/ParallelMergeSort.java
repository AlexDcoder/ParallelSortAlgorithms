package parallel.merge_sort;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort extends RecursiveAction{
    private int[] array;
    private int start;
    private int end;
    private int mid = array.length / 2;
    private static final int THRESHOLD = 2;
    
    public ParallelMergeSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if(end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                
            }
        } else {

        } 
    }

    private void merge() {

    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
    }

    

}