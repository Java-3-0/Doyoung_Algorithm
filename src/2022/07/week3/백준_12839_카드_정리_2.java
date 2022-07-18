// 16360KB, 204ms

package boj12839;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 50, MAX_M = 14;
    static final int MAX_BITMASK = (1 << MAX_M) - 1;
    static final int CACHE_EMPTY = -1;
    static final int INF = 987654321;

    static int N, M;
    static int targetBitMask;
    static int[][] boxes = new int[MAX_N + 1][MAX_M + 1];
    static int[] boxSums = new int[MAX_N + 1];
    static int[][] cache = new int[MAX_N + 1][MAX_BITMASK + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 캐시 초기화
        initCache();

        // N, M 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 타겟 비트마스크 계산
        targetBitMask = (1 << M) - 1;

        // 박스 정보 입력
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine(), " ");

            for (int m = 0; m < M; m++) {
                int cnt = Integer.parseInt(st.nextToken());
                boxes[n][m] = cnt;
                boxSums[n] += cnt;
            }
        }

        // 정답 계산
        int answer = getMinMoves(0, 0);

        // 출력
        System.out.println(answer);
    }

    private static int getMinMoves(int boxIdx, int usedColors) {
        if (boxIdx == N) {
            if (usedColors == targetBitMask) {
                return 0;
            }
            else {
                return INF;
            }
        }

        if (cache[boxIdx][usedColors] != CACHE_EMPTY) {
            return cache[boxIdx][usedColors];
        }

        int ret = INF;

        ret = Math.min(ret, boxSums[boxIdx] + getMinMoves(boxIdx + 1, usedColors));

        for (int pick = 0; pick < M; pick++) {
            if ((usedColors & (1 << pick)) == 0) {
                ret = Math.min(ret, (boxSums[boxIdx] - boxes[boxIdx][pick]) + getMinMoves(boxIdx + 1, usedColors | (1 << pick)));
            }
        }

        return cache[boxIdx][usedColors] = ret;
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }
}