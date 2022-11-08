import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_INTERVAL = 18;
    static final double CACHE_EMPTY = -1.0;
    static double A, B;
    static boolean[] isPrime = new boolean[MAX_INTERVAL + 1];
    static double[][][] cache = new double[MAX_INTERVAL + 1][MAX_INTERVAL + 1][MAX_INTERVAL + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        precalcPrimes();

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            initCache();

            st = new StringTokenizer(br.readLine(), " ");
            A = Double.parseDouble(st.nextToken()) / 100.0;
            B = Double.parseDouble(st.nextToken()) / 100.0;

            double answer = getPossibility(0, 0, 0);

            sb.append("#").append(tc).append(" ").append(String.format("%.6f", answer)).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void precalcPrimes() {
        isPrime[2] = true;
        isPrime[3] = true;
        isPrime[5] = true;
        isPrime[7] = true;
        isPrime[11] = true;
        isPrime[13] = true;
        isPrime[17] = true;
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int k = 0; k < cache[i].length; k++) {
                Arrays.fill(cache[i][k], CACHE_EMPTY);
            }
        }
    }

    public static double getPossibility(int idx, int cntA, int cntB) {
        if (idx == MAX_INTERVAL) {
            return (isPrime[cntA] || isPrime[cntB]) ? 1.0 : 0.0;
        }

        if (cache[idx][cntA][cntB] != CACHE_EMPTY) {
            return cache[idx][cntA][cntB];
        }

        double possibilityAB = A * B * getPossibility(idx + 1, cntA + 1, cntB + 1);
        double possibilityA = A * (1.0 - B) * getPossibility(idx + 1, cntA + 1, cntB);
        double possibilityB = (1.0 - A) * B * getPossibility(idx + 1, cntA, cntB + 1);
        double possibilityNone = (1.0 - A) * (1.0 - B) * getPossibility(idx + 1, cntA, cntB);

        double ret = possibilityAB + possibilityA + possibilityB + possibilityNone;

        return cache[idx][cntA][cntB] = ret;
    }
}