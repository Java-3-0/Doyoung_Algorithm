// 19340KB, 1444ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final long INF = 987654321098765L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine(), " ");
        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        long[][] grid = new long[H + 1][W + 1];
        for (int y = 1; y <= H; y++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int x = 1; x <= W; x++) {
                grid[y][x] = Long.parseLong(st.nextToken());
            }
        }

        long[][] psums = new long[H + 1][W + 1];

        // 각 row 의 누적합을 psums 에 저장
        for (int y = 1; y <= H; y++) {
            long sum = 0L;
            for (int x = 1; x <= W; x++) {
                sum += grid[y][x];

                psums[y][x] = sum;
            }
        }

        // row 의 누적합을 더해서 (1, 1)에서 (y, x)까지의 누적합을 psums[y][x]에 가지도록 한다
        for (int y = 1; y <= H; y++) {
            for (int x = 1; x <= W; x++) {
                psums[y][x] += psums[y - 1][x];
            }
        }

        long maxSum = -INF;
        for (int y1 = 1; y1 <= H; y1++) {
            for (int x1 = 1; x1 <= W; x1++) {
                for (int y2 = y1; y2 <= H; y2++) {
                    for (int x2 = x1; x2 <= W; x2++) {
                        long sum = getSum(psums, y1, x1, y2, x2);

                        maxSum = Math.max(maxSum, sum);
                    }
                }
            }
        }

        System.out.println(maxSum);
    }

    public static long getSum(long[][] psums, int y1, int x1, int y2, int x2) {
        return psums[y2][x2] - psums[y2][x1 - 1] - psums[y1 - 1][x2] + psums[y1 - 1][x1 - 1];
    }
}
