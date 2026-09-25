import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. Алгоритмдерді тексеру (Unit Tests) ===");
        runCorrectnessTests();

        System.out.println("\n=== 2. Эксперименттерді бастау ===");
        Experiment.runExperiments();
    }

    private static void runCorrectnessTests() {
        // MergeSort & QuickSort тестілеу
        int[] testArr = {5, 2, 9, 1, 5, 6, 3, -1, 0};
        int[] expected = testArr.clone();
        Arrays.sort(expected);

        MergeSorter ms = new MergeSorter();
        int[] msArr = testArr.clone();
        ms.sort(msArr);
        assert Arrays.equals(msArr, expected) : "MergeSort қате жұмыс істеді!";

        QuickSorter qs = new QuickSorter();
        int[] qsArr = testArr.clone();
        qs.sort(qsArr);
        assert Arrays.equals(qsArr, expected) : "QuickSort қате жұмыс істеді!";

        // Deterministic Select тестілеу
        DeterministicSelector ds = new DeterministicSelector();
        int[] selArr = testArr.clone();
        int val = ds.select(selArr, 4); // 4-ші индекстегі элемент (медиана)
        assert val == expected[4] : "Deterministic Select қате!";

        // Closest Pair тестілеу
        Point[] points = { new Point(0,0), new Point(1,1), new Point(2,2), new Point(10,10) };
        ClosestPairSolver cp = new ClosestPairSolver();
        double fastDist = cp.solveFast(points);
        double bruteDist = cp.bruteForce(points);
        assert Math.abs(fastDist - bruteDist) < 1e-6 : "Closest Pair қате!";

        System.out.println("Барлық тексеру тестілері сәтті өтті (Correctness OK)!");
    }
}