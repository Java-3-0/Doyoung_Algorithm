import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // N, K 입력
        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        // 주어진 수열에서 K를 빼서 입력받음
        long[] seq = new long[N + 1];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= N; i++) {
            seq[i] = Long.parseLong(st.nextToken()) - K;
        }

        // 누적합 계산
        long[] psums = new long[N + 1];
        long sum = 0L;
        for (int i = 1; i <= N; i++) {
            sum += seq[i];
            psums[i] = sum;
        }

        // 누적합 - 누적합 = 0인 구간의 개수를 찾기
        Map<Long, Long> countMap = new HashMap<>();
        for (int i = 0; i <= N; i++) {
            countMap.put(psums[i], 1L + countMap.getOrDefault(psums[i], 0L));
        }
        long answer = 0L;
        for (long value : countMap.values()) {
            if (value >= 2) {
                answer += value * (value - 1L) / 2L;
            }
        }

        System.out.println(answer);

    } // end main

}