public class MergeSorter {

    private static final int CUTOFF = 10;
    private long comparisons = 0;
    private int maxRecursionDepth = 0;

    public void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a == null || a.length <= 1) return;

        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, 1);
    }

    private void sort(int[] a, int[] aux, int low, int high, int currentDepth) {
        if (currentDepth > maxRecursionDepth) {
            maxRecursionDepth = currentDepth;
        }

        if (high - low + 1 <= CUTOFF) {
            insertionSort(a, low, high);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid, currentDepth + 1);
        sort(a, aux, mid + 1, high, currentDepth + 1);

        comparisons++;
        if (a[mid] <= a[mid + 1]) return;

        merge(a, aux, low, mid, high);
    }

    private void merge(int[] a, int[] aux, int low, int mid, int high) {
        System.arraycopy(a, low, aux, low, high - low + 1);

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else {
                comparisons++;
                if (aux[j] < aux[i]) {
                    a[k] = aux[j++];
                } else {
                    a[k] = aux[i++];
                }
            }
        }
    }

    private void insertionSort(int[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}