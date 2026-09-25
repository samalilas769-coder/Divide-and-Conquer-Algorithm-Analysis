import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Experiment {

    private static final Random random = new Random();

    public static void runExperiments() {
        String csvFile = "results/results.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // CSV файлының заголовкасы
            writer.println("Algorithm,InputType,Size,ExecutionTimeNanos,RecursionDepth,ComparisonsOrSwaps");

            int[] sizes = {100, 1000, 10000, 50000};
            String[] types = {"Random", "Sorted", "Reverse-sorted", "Duplicate-heavy"};

            MergeSorter mergeSorter = new MergeSorter();
            QuickSorter quickSorter = new QuickSorter();
            DeterministicSelector selector = new DeterministicSelector();

            // 1. MergeSort & QuickSort & Selector эксперименттері
            for (int size : sizes) {
                for (String type : types) {
                    int[] baseArray = generateArray(size, type);

                    // --- MergeSort ---
                    int[] a1 = baseArray.clone();
                    long start = System.nanoTime();
                    mergeSorter.sort(a1);
                    long duration = System.nanoTime() - start;
                    writer.printf("MergeSort,%s,%d,%d,%d,%d\n",
                            type, size, duration, mergeSorter.getMaxRecursionDepth(), mergeSorter.getComparisons());

                    // --- QuickSort ---
                    int[] a2 = baseArray.clone();
                    start = System.nanoTime();
                    quickSorter.sort(a2);
                    duration = System.nanoTime() - start;
                    writer.printf("QuickSort,%s,%d,%d,%d,%d\n",
                            type, size, duration, quickSorter.getMaxRecursionDepth(), quickSorter.getComparisons());

                    // --- Deterministic Select (k = n/2) ---
                    int[] a3 = baseArray.clone();
                    start = System.nanoTime();
                    selector.select(a3, size / 2);
                    duration = System.nanoTime() - start;
                    writer.printf("DeterministicSelect,%s,%d,%d,%d,%d\n",
                            type, size, duration, selector.getMaxRecursionDepth(), selector.getComparisons());
                }
            }

            // 2. Closest Pair эксперименттері
            ClosestPairSolver closestSolver = new ClosestPairSolver();
            int[] pointSizes = {100, 500, 1000, 2000, 5000};

            for (int size : pointSizes) {
                Point[] points = generateRandomPoints(size);

                long start = System.nanoTime();
                closestSolver.solveFast(points);
                long duration = System.nanoTime() - start;

                writer.printf("ClosestPair,RandomPoints,%d,%d,%d,0\n",
                        size, duration, closestSolver.getMaxRecursionDepth());
            }

            System.out.println("Эксперименттер сәтті аяқталды! Нәтижелер 'results/results.csv' файлына сақталды.");

        } catch (IOException e) {
            System.err.println("CSV файлына жазу кезінде қате шықты: " + e.getMessage());
        }
    }

    // Түрлі массив түрлерін генерациялау
    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(size * 10);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reverse-sorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Duplicate-heavy":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(5); // Тек 0..4 арасындағы сандар
                break;
        }
        return arr;
    }

    // Кездейсоқ нүктелерді генерациялау
    private static Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }
}