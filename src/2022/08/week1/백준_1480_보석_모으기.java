// 20224KB, 248ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 13, MAX_M = 10, MAX_C = 20;
    static final int MAX_BITMASK = (1 << MAX_N) - 1;
    static final int CACHE_EMPTY = -1;
    static int N, M, C;
    static int[] jewelWeights = new int[MAX_N];
    static int[][][] cache = new int[MAX_M + 1][MAX_C + 1][MAX_BITMASK + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 캐시 초기화
        initCache();

        // N, M, C 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 보석 무게들 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            jewelWeights[i] = Integer.parseInt(st.nextToken());
        }

        int answer = solve(0, C, 0);

        System.out.println(answer);

    } // end main

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < cache[i].length; j++) {
                Arrays.fill(cache[i][j], CACHE_EMPTY);
            }
        }
    }

    public static int solve(int bagIdx, int remainingCapacity, int usedJewels) {
        if (bagIdx == M) {
            return 0;
        }

        if (cache[bagIdx][remainingCapacity][usedJewels] != CACHE_EMPTY) {
            return cache[bagIdx][remainingCapacity][usedJewels];
        }

        int ret = 0;

        // 아무것도 더 안 담고 다음 가방으로 넘어가는 경우
        ret = Math.max(ret, solve(bagIdx + 1, C, usedJewels));

        // 이 가방에 보석을 담는 것을 시도하는 경우
        for (int i = 0; i < N; i++) {
            if ((usedJewels & (1 << i)) == 0) {
                int weight = jewelWeights[i];
                if (weight <= remainingCapacity) {
                    ret = Math.max(ret, 1 + solve(bagIdx, remainingCapacity - weight, (usedJewels | (1 << i))));
                }
            }
        }

        return cache[bagIdx][remainingCapacity][usedJewels] = ret;

    }

}