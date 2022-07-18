// 32764KB, 132ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 15;
    static final int MAX_BITMASK = (1 << MAX_N) - 1;
    static final int CACHE_EMPTY = -1;
    static final int MAX_PRICE = 9;

    static int N;
    static int[][] adjMatrix = new int[MAX_N][MAX_N];
    static int[][][] cache = new int[MAX_N + 1][MAX_PRICE + 1][MAX_BITMASK + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 캐시 초기화
        initCache();

        // 예술가의 수 입력
        N = Integer.parseInt(br.readLine());

        // 그림 거래 가격 입력
        for (int y = 0; y < N; y++) {
            String s = br.readLine();
            for (int x = 0; x < N; x++) {
                adjMatrix[y][x] = s.charAt(x) - '0';
            }
        }

        // 정답 계산 및 출력
        int answer = getMaxDeals(0, 0, 1);

        System.out.println(answer);

    }

    public static int getMaxDeals(int owner, int possiblePrice, int bitMask) {
        if (possiblePrice > MAX_PRICE) {
            return 0;
        }

        if (cache[owner][possiblePrice][bitMask] != CACHE_EMPTY) {
            return cache[owner][possiblePrice][bitMask];
        }

        int ret = 1;

        for (int nextOwner = 0; nextOwner < N; nextOwner++) {
            int nextPrice = adjMatrix[owner][nextOwner];

            if ((bitMask & (1 << nextOwner)) == 0 && nextPrice >= possiblePrice) {
                int deals = 1 + getMaxDeals(nextOwner, nextPrice, (bitMask | (1 << nextOwner)));
                ret = Math.max(ret, deals);
            }
        }

        return cache[owner][possiblePrice][bitMask] = ret;
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int k = 0; k < cache[i].length; k++) {
                Arrays.fill(cache[i][k], CACHE_EMPTY);
            }
        }
    }

}
