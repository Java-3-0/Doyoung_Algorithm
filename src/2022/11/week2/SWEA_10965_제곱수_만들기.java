import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static final int MAX_N = 10000000;
    static final int SQRT_MAX_N = (int) Math.floor(Math.sqrt(MAX_N));

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 소수 제곱들 구하기
        List<Integer> primeSquares = getPrimeSquares();

        // 테스트케이스 수행
        final int TESTS = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= TESTS; tc++) {
            int N = Integer.parseInt(br.readLine());

            // 소수 제곱들로 최대한 나누고 남은만큼이 정답
            int answer = N;
            for (int num : primeSquares) {
                while (answer % num == 0) {
                    answer /= num;
                }
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static List<Integer> getPrimeSquares() {
        // 에라토스테네스의 체
	boolean[] isPrime = new boolean[SQRT_MAX_N + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        int sqrt = (int) Math.floor(Math.sqrt(SQRT_MAX_N));
        for (int div = 2; div <= sqrt; div++) {
            if (!isPrime[div]) {
                continue;
            }
            for (int num = div * div; num <= SQRT_MAX_N; num += div) {
                isPrime[num] = false;
            }
        }

        // 소수 제곱들을 리스트에 담아서 리턴
        List<Integer> ret = new ArrayList<>();
        for (int num = 0; num < SQRT_MAX_N; num++) {
            if (isPrime[num]) {
                int square = num * num;
                ret.add(square);
            }
        }

        return ret;
    }


}