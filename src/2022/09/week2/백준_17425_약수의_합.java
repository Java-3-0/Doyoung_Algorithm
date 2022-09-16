import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int MAX_N = 1000000;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // f, g 전처리
        long[] f = getF();
        long[] g = getPrefixSum(f);

        // 테스트케이스 수행
        final int TESTS = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= TESTS; tc++) {
            int N = Integer.parseInt(br.readLine());
            long answer = g[N];

            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static long[] getF() {
        long[] ret = new long[MAX_N + 1];

        for (int div = 1; div <= MAX_N; div++) {
            for (int num = div; num <= MAX_N; num += div) {
                ret[num] += div;
            }
        }

        return ret;
    }

    public static long[] getPrefixSum(long[] arr) {
        long[] ret = new long[MAX_N + 1];

        // 누적합 구하기
        long sum = 0L;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            sum += arr[i];
            ret[i] = sum;
        }

        return ret;
    }

}