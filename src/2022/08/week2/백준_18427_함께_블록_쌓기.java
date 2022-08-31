import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 50, MAX_M = 10, MAX_H = 1000;
    static final int MOD = 10007;
    static final int CACHE_EMPTY = -1;
    static int N, M, H;

    static List<Integer>[] blockHeights = new ArrayList[MAX_N];
    static int[][] cache = new int[MAX_N + 1][MAX_H + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // blockHeights 메모리 할당
        for (int i = 0; i < blockHeights.length; i++) {
            blockHeights[i] = new ArrayList<Integer>();
        }

        // N, M, H 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        // 학생별 블록 정보 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            while (st.hasMoreTokens()) {
                int height = Integer.parseInt(st.nextToken());
                blockHeights[i].add(height);
            }
        }

        initCache();

        long answer = solve(0, H);

        System.out.print(answer);
    }

    public static void initCache() {
        for (int i = 0; i  <cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }

    public static int solve(int startIdx, int remainingHeight) {
        if (remainingHeight == 0) {
            return 1;
        }

        if (startIdx == N) {
            return 0;
        }

        if (cache[startIdx][remainingHeight] != CACHE_EMPTY) {
            return cache[startIdx][remainingHeight];
        }

        int ret = 0;

        // 아무것도 안 쓰는 경우
        ret += solve(startIdx + 1, remainingHeight);
        ret %= MOD;

        // 블록을 하나 쓰는 경우
        for (int blockHeight : blockHeights[startIdx]) {
            if (blockHeight <= remainingHeight) {
                ret += solve(startIdx + 1, remainingHeight - blockHeight);
                ret %= MOD;
            }
        }

        return cache[startIdx][remainingHeight] = ret;

    }
}
