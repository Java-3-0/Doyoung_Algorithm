import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int MAX_LEN = 500;
    static final int CACHE_EMPTY = -1;

    static int len;
    static char[] text1;
    static char[] text2;

    static int[][] cache = new int[MAX_LEN][MAX_LEN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= TESTS; tc++) {
            initCache();

            len = Integer.parseInt(br.readLine().trim());
            text1 = br.readLine().toCharArray();
            text2 = br.readLine().toCharArray();

            int lcsLength = getLengthOfLCS(0, 0);

            double answer = (double) lcsLength / (double) len * 100.0;

            sb.append("#").append(tc).append(" ").append(String.format("%.2f", answer)).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }
    }

    public static int getLengthOfLCS(int startIdx1, int startIdx2) {
        // base case
        if (startIdx1 == len || startIdx2 == len) {
            return 0;
        }

        // memoization
        if (cache[startIdx1][startIdx2] != CACHE_EMPTY) {
            return cache[startIdx1][startIdx2];
        }

        // 새로 계산
        int ret = 1;
        if (text1[startIdx1] == text2[startIdx2]) {
            ret = 1 + getLengthOfLCS(startIdx1 + 1, startIdx2 + 1);
        } else {
            int ret1 = getLengthOfLCS(startIdx1 + 1, startIdx2);
            int ret2 = getLengthOfLCS(startIdx1, startIdx2 + 1);

            ret = Math.max(ret1, ret2);
        }

        return cache[startIdx1][startIdx2] = ret;
    }
}