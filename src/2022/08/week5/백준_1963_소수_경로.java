// 12544KB, 108ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_NUM = 9999, MIN_NUM = 1000;

    static final int IMPOSSIBLE = -1;

    static boolean[] isPrime = new boolean[MAX_NUM + 1];
    static Queue<Integer> queue = new ArrayDeque<>();
    static boolean[] isVisited = new boolean[MAX_NUM + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 소수 여부 미리 계산
        precalcPrimes();

        // 테스트케이스 처리
        final int TESTS = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= TESTS; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int start = Integer.parseInt(st.nextToken());
            int target = Integer.parseInt(st.nextToken());

            int answer = solve(start, target);

            if (answer == IMPOSSIBLE) {
                sb.append("Impossible");
            } else {
                sb.append(answer);
            }
            sb.append("\n");
        }

        // 출력
        System.out.print(sb.toString());
    }

    public static int solve(int start, int target) {
        // 초기화
        queue.clear();
        Arrays.fill(isVisited, false);

        // 시작 정점 처리
        isVisited[start] = true;
        queue.offer(start);

        // bfs 수행
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int here = queue.poll();
                if (here == target) {
                    return depth;
                }

                // pos 위치를 digit 로 바꿔 본다.
                for (int pos = 0; pos < 4; pos++) {
                    for (int digit = 0; digit < 10; digit++) {
                        int next = getNextNumber(here, pos, digit);

                        if (isInRange(next) && isPrime[next] && !isVisited[next]) {
                            isVisited[next] = true;
                            queue.offer(next);
                        }
                    }
                }
            }

            depth++;
        }

        return IMPOSSIBLE;
    }

    /**
     * 4자리 수인지 여부를 리턴
     */
    public static boolean isInRange(int num) {
        if (MIN_NUM <= num && num <= MAX_NUM) {
            return true;
        }
        return false;
    }

    /**
     * 소수 여부를 미리 계산하여 isPrime 배열에 저장
     */
    public static void precalcPrimes() {
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        int sqrt = (int) Math.floor(Math.sqrt(MAX_NUM));

        for (int num = 2; num <= sqrt; num++) {
            if (isPrime[num]) {
                for (int k = num * num; k <= MAX_NUM; k += num) {
                    isPrime[k] = false;
                }
            }
        }
    }

    /**
     * orignal 의 pos 위치를 digit 으로 바꾼 수를 리턴
     */
    public static int getNextNumber(int original, int pos, int digit) {
        int unit = (int) Math.pow(10, pos);

        int ret = original - (((original / unit) % 10) * unit) + (digit * unit);

        return ret;
    }
}
