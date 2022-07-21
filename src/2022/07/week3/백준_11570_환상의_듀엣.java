// 45524KB, 316ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 2000;
    static final long INF = 987654321098765L;
    static final long CACHE_EMPTY = -1;
    static final int NO_MUSIC = 0;

    static int N;
    static long[] seq = new long[MAX_N + 1];
    static long[][] cache = new long[MAX_N + 1][MAX_N + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br=  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 수열 길이 입력
        N = Integer.parseInt(br.readLine());

        // 수열 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= N; i++) {
            seq[i] = Long.parseLong(st.nextToken());
        }

        // 캐시 초기화
        initCache();

        // 정답 계산
        long answer = getMinDifficulty(NO_MUSIC, NO_MUSIC);

        // 출력
        System.out.println(answer);
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }

    public static long getMinDifficulty(int lastA, int lastB) {
        // 지금 불러야 할 음 번호 계산
        int idxNow = Math.max(lastA, lastB) + 1;

        // 1. base case
        if (idxNow > N) {
            return 0;
        }

        // 2. memoization
        if (cache[lastA][lastB] != CACHE_EMPTY) {
            return cache[lastA][lastB];
        }

        // 3. 새로 계산해야 하는 경우
        long ret = INF;

        // A가 부르는 경우
        if (lastA == NO_MUSIC) {
            ret = Math.min(ret, getMinDifficulty(idxNow, lastB));
        }
        else {
            ret = Math.min(ret, getDifference(lastA, idxNow) + getMinDifficulty(idxNow, lastB));
        }

        // B가 부르는 경우
        if (lastB == NO_MUSIC) {
            ret = Math.min(ret, getMinDifficulty(lastA, idxNow));
        }
        else {
            ret = Math.min(ret, getDifference(lastB, idxNow) + getMinDifficulty(lastA, idxNow));
        }

        return cache[lastA][lastB] = ret;

    }

    public static long getDifference(int idx1, int idx2) {
        return Math.abs(seq[idx2] - seq[idx1]);
    }
}
