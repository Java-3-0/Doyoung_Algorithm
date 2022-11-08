import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 2000;
    static final long MAX_K = (long) 1e9, MOD = (long) (1e9 + 7);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            // 입력
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            long K = Long.parseLong(st.nextToken());

            int[] seq = new int[N];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                seq[i] = Integer.parseInt(st.nextToken());
            }

            // 작은 수 개수 합산
            int cntLeft = 0, cntRight = 0;;
            for (int left = 0; left < N; left++) {
                for (int right = left + 1; right < N; right++) {
                    if (seq[left] < seq[right]) {
                        cntLeft++;
                    }
                    else if (seq[left] > seq[right]) {
                        cntRight++;
                    }
                }
            }

            // 정답 계산 및 출력
            long totalLeft = modMultiply(((K - 1L) * K / 2L) % MOD, cntLeft);
            long totalRight = modMultiply((K * (K + 1L) / 2L) % MOD, cntRight);
            long answer = modAdd(totalLeft, totalRight);

            sb.append("#").append(tc).append(" ").append(answer).append("\n");

        }

        System.out.print(sb.toString());

    } // end main

    public static long modAdd(long a, long b) {
        return (a + b) % MOD;
    }

    public static long modMultiply(long a, long b) {
        return (a * b) % MOD;
    }

}