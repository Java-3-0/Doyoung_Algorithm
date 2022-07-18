package boj2240;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_T = 1000;
    static final int MAX_W = 30;
    static final int POSITIONS = 2;
    static final int CACHE_EMPTY = -1;

    static int T, W;
    static int[] seq = new int[MAX_T + 1];
    static int[][][] cache = new int[MAX_T + 1][POSITIONS + 1][MAX_W + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        initCache();

        st = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        for (int i = 0; i < T; i++) {
            seq[i] = Integer.parseInt(br.readLine());
        }

        int answer = getMaxApples(0, 1, 0);

        System.out.println(answer);

    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int k = 0; k < cache[i].length; k++) {
                Arrays.fill(cache[i][k], CACHE_EMPTY);
            }
        }
    }

    public static int getMaxApples(int startTime, int currentPos, int moveCnt) {
        if (startTime >= T) {
            return 0;
        }

        if (cache[startTime][currentPos][moveCnt] != CACHE_EMPTY) {
            return cache[startTime][currentPos][moveCnt];
        }


        int applePos = seq[startTime];
        int ret = 0;

        // 있던 자리에 사과가 떨어지는 경우
        if (applePos == currentPos) {
            ret = 1 + getMaxApples(startTime + 1, currentPos, moveCnt);
        }
        // 다른 자리에 사과가 떨어지는 경우
        else {
            // 이동하지 않는 경우
            ret = getMaxApples(startTime + 1, currentPos, moveCnt);
            // 이동하는 경우
            if (moveCnt < W) {
                ret = Math.max(ret, 1 + getMaxApples(startTime + 1, applePos, moveCnt + 1));
            }
        }

        return cache[startTime][currentPos][moveCnt] = ret;
    }
}
