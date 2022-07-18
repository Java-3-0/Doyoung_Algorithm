package boj4811;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int MAX_TESTS = 1000;
    static final int MAX_N = 30;
    static final long CACHE_EMPTY = -1L;

    static int N;
    static long[][] cache = new long[MAX_N + 1][MAX_N + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            initCache();

            int N = Integer.parseInt(br.readLine());

            if (N == 0) {
                break;
            }

            long answer = solve(N, 0);

            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString());
    }

    public static long solve(int fullCnt, int halfCnt) {
        if (fullCnt == 0 && halfCnt == 0) {
            return 1L;
        }

        if (cache[fullCnt][halfCnt] != CACHE_EMPTY) {
            return cache[fullCnt][halfCnt];
        }

        long ret = 0L;

        if (fullCnt > 0) {
            ret += solve(fullCnt - 1, halfCnt + 1);
        }
        if (halfCnt > 0) {
            ret += solve(fullCnt, halfCnt - 1);
        }

        return cache[fullCnt][halfCnt] = ret;
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }
}
