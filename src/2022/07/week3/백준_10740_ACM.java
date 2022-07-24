// 8565Kb, 796ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 150000;
    static final int PEOPLE = 3;
    static final int MAX_BITMASK = (1 << PEOPLE) - 1;
    static final int CACHE_EMPTY = -1;
    static final int INF = 987654321;
    static int N;
    static int[][] difficulties = new int[PEOPLE][MAX_N];
    static int[][][] cache = new int[PEOPLE + 1][MAX_N + 1][MAX_BITMASK + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 캐시 초기화
        initCache();

        // N 입력
        N = Integer.parseInt(br.readLine());

        // 사람마다 문제 난이도 정보 입력
        for (int y = 0; y < PEOPLE; y++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int x = 0; x < N; x++) {
                difficulties[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = INF;
        for (int firstPerson = 0; firstPerson < PEOPLE; firstPerson++) {
            int diff = difficulties[firstPerson][0] + getMinSum(firstPerson, 1, (1 << firstPerson));
            answer = Math.min(answer, diff);
        }

        System.out.println(answer);
    }

    public static int getMinSum(int prevPerson, int startIdx, int used) {
        // base case
        if (startIdx == N) {
            if (used == MAX_BITMASK) {
                return 0;
            } else {
                return INF;
            }
        }

        // memoization
        if (cache[prevPerson][startIdx][used] != CACHE_EMPTY) {
            return cache[prevPerson][startIdx][used];
        }

        // 새로 계산
        int ret = INF;

        for (int person = 0; person < PEOPLE; person++) {
            // 이전 문제를 푼 사람이 계속 푸는 경우
            if (person == prevPerson) {
                ret = Math.min(ret, difficulties[person][startIdx] + getMinSum(person, startIdx + 1, used));
            }
            // 다른 사람이 문제를 푸는 경우
            else {
                // 이전에 푼 적이 없는 사람만 풀 수 있다.
                if ((used & (1 << person)) == 0) {
                    ret = Math.min(ret, difficulties[person][startIdx] + getMinSum(person, startIdx + 1, (used | (1 << person))));
                }
            }
        }

        return cache[prevPerson][startIdx][used] = ret;
    }

    public static void initCache() {
        for (int i = 0; i < cache.length; i++) {
            for (int k = 0; k < cache[i].length; k++) {
                Arrays.fill(cache[i][k], CACHE_EMPTY);
            }
        }
    }
}
