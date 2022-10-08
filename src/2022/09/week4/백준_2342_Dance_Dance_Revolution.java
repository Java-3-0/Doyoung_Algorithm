// 56172KB, 488ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int POSITIONS = 5;
    static final int CACHE_EMPTY = -1;
    static int[][] costs = new int[POSITIONS][];
    static List<Integer> targetList = new ArrayList<>();
    static int listSize;
    static int[][][] cache;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // costs 설정
        costs[0] = new int[]{1, 2, 2, 2, 2};
        costs[1] = new int[]{3, 1, 3, 4, 3};
        costs[2] = new int[]{3, 3, 1, 3, 4};
        costs[3] = new int[]{3, 4, 3, 1, 3};
        costs[4] = new int[]{3, 3, 4, 3, 1};

        // 입력
        st = new StringTokenizer(br.readLine(), " ");
        while (true) {
            int N = Integer.parseInt(st.nextToken());
            if (N == 0) {
                break;
            } else {
                targetList.add(N);
            }
        }

        listSize = targetList.size();

        // cache 설정
        cache = new int[listSize][POSITIONS][POSITIONS];

        // cache 초기화
        initCache();

        // 정답 계산
        int answer = calcMinPower(0, 0, 0);

        // 출력
        System.out.println(answer);

    } // end main

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int k = 0; k < cache[i].length; k++) {
                Arrays.fill(cache[i][k], CACHE_EMPTY);
            }
        }
    }

    public static int calcMinPower(int idx, int foot1, int foot2) {
        if (idx == listSize) {
            return 0;
        }

        if (cache[idx][foot1][foot2] != CACHE_EMPTY) {
            return cache[idx][foot1][foot2];
        }

        int target = targetList.get(idx);

        int power1 = costs[foot1][target] + calcMinPower(idx + 1, target, foot2);
        int power2 = costs[foot2][target] + calcMinPower(idx + 1, foot1, target);
        int ret = Math.min(power1, power2);
        return cache[idx][foot1][foot2] = ret;
    }

}