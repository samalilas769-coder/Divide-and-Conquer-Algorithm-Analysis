import java.util.Random;

public class QuickSorter {

    private final Random random = new Random();
    private long comparisons = 0;
    private long swaps = 0;
    private int maxRecursionDepth = 0;

    public void sort(int[] a) {
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;
        if (a == null || a.length <= 1) return;

        sort(a, 0, a.length - 1, 1);
    }

    private void sort(int[] a, int low, int high, int currentDepth) {
        while (low < high) {
            if (currentDepth > maxRecursionDepth) {
                maxRecursionDepth = currentDepth;
            }

            // Рандомизацияланған pivot
            int pIndex = low + random.nextInt(high - low + 1);
            swap(a, pIndex, high);

            int p = partition(a, low, high);

            // Кіші бөлігіне рекурсивті өту, ал үлкен бөлігін while циклымен өңдеу
            if (p - low < high - p) {
                sort(a, low, p - 1, currentDepth + 1);
                low = p + 1;
            } else {
                sort(a, p + 1, high, currentDepth + 1);
                high = p - 1;
            }
        }
    }

    private int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (a[j] <= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        swaps++;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}