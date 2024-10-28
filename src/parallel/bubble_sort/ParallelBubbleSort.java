package parallel.bubble_sort;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelBubbleSort extends RecursiveAction{
    private int[] array;
    private int start;
    private int end;
    private static final int THRESHOLD = 10;

    public ParallelBubbleSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {

        }  else {

        } 
    }

    public static void main(String[] args) {
        
    }

    
}
