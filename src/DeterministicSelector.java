import java.util.Arrays;

public class DeterministicSelector {

    private long comparisons = 0;
    private int maxRecursionDepth = 0;

    public int select(int[] a, int k) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a == null || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input or index k");
        }
        return select(a, 0, a.length - 1, k, 1);
    }

    private int select(int[] a, int low, int high, int k, int currentDepth) {
        if (currentDepth > maxRecursionDepth) {
            maxRecursionDepth = currentDepth;
        }

        if (low == high) return a[low];

        int pivot = medianOfMedians(a, low, high);
        int p = partition(a, low, high, pivot);

        if (k == p) {
            return a[p];
        } else if (k < p) {
            return select(a, low, p - 1, k, currentDepth + 1);
        } else {
            return select(a, p + 1, high, k, currentDepth + 1);
        }
    }

    private int medianOfMedians(int[] a, int low, int high) {
        int n = high - low + 1;
        if (n <= 5) {
            return findMedian5(a, low, high);
        }

        int numGroups = (int) Math.ceil((double) n / 5);
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int subLow = low + i * 5;
            int subHigh = Math.min(subLow + 4, high);
            medians[i] = findMedian5(a, subLow, subHigh);
        }

        return select(medians, 0, medians.length - 1, medians.length / 2, 1);
    }

    private int findMedian5(int[] a, int low, int high) {
        int[] temp = Arrays.copyOfRange(a, low, high + 1);
        Arrays.sort(temp);
        return temp[temp.length / 2];
    }

    private int partition(int[] a, int low, int high, int pivot) {
        for (int i = low; i <= high; i++) {
            if (a[i] == pivot) {
                swap(a, i, high);
                break;
            }
        }

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
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}