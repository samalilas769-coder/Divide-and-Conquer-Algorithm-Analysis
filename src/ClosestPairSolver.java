import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    private int maxRecursionDepth = 0;

    // Ең жақын екі нүктенің арақашықтығын қайтарады
    public double solveFast(Point[] points) {
        maxRecursionDepth = 0;
        if (points == null || points.length < 2) return Double.POSITIVE_INFINITY;

        // X координатасы бойынша сұрыптау
        Point[] pointsSortedByX = points.clone();
        Arrays.sort(pointsSortedByX, Comparator.comparingDouble(p -> p.x));

        // Y координатасы бойынша сұрыптау
        Point[] pointsSortedByY = points.clone();
        Arrays.sort(pointsSortedByY, Comparator.comparingDouble(p -> p.y));

        return closest(pointsSortedByX, pointsSortedByY, 1);
    }

    private double closest(Point[] pointsByX, Point[] pointsByY, int currentDepth) {
        if (currentDepth > maxRecursionDepth) {
            maxRecursionDepth = currentDepth;
        }

        int n = pointsByX.length;
        // Элемент азайғанда наивті методқа ауысу
        if (n <= 3) {
            return bruteForce(pointsByX);
        }

        int mid = n / 2;
        Point midPoint = pointsByX[mid];

        Point[] leftByX = Arrays.copyOfRange(pointsByX, 0, mid);
        Point[] rightByX = Arrays.copyOfRange(pointsByX, mid, n);

        // Y бойынша сұрыпталған массивті екіге бөлу
        Point[] leftByY = new Point[leftByX.length];
        Point[] rightByY = new Point[rightByX.length];
        int l = 0, r = 0;
        for (Point p : pointsByY) {
            if (p.x <= midPoint.x && l < leftByX.length) {
                leftByY[l++] = p;
            } else {
                rightByY[r++] = p;
            }
        }

        double dLeft = closest(leftByX, leftByY, currentDepth + 1);
        double dRight = closest(rightByX, rightByY, currentDepth + 1);
        double d = Math.min(dLeft, dRight);

        // Ортадағы полосаны (Strip) тексеру
        Point[] strip = new Point[n];
        int k = 0;
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip[k++] = p;
            }
        }

        // Полосаның ішіндегі нүктелерді тексеру (максимум 7-8 көрші)
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k && (strip[j].y - strip[i].y) < d; j++) {
                double dist = strip[i].distanceTo(strip[j]);
                if (dist < d) {
                    d = dist;
                }
            }
        }

        return d;
    }

    // Тексеру (Testing) және салыстыру үшін Bruteforce O(n^2) методы
    public double bruteForce(Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}