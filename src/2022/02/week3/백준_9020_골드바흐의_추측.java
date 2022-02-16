// 13052KB, 116ms

package bj9020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 10000;
	public static boolean[] isPrime = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 10000 이하의 모든 소수 미리 계산해둠
		setIsPrime();

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			int N = Integer.parseInt(br.readLine());

			// 골드바흐 파티션 계산
			int[] answer = goldbach(N);

			// 출력에 저장
			sb.append(answer[0]).append(" ").append(answer[1]).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

	/** 파라미터로 받은 정수의 골드바흐 파티션을 리턴 */
	public static int[] goldbach(int n) {
		int[] ret = new int[2];

		int half = n / 2;

		for (int num1 = half; num1 >= 0; num1--) {
			int num2 = n - num1;
			if (isPrime[num1] && isPrime[num2]) {
				ret[0] = num1;
				ret[1] = num2;
				return ret;
			}
		}

		return ret;
	}

	/** isPrime 배열을 설정한다 */
	public static void setIsPrime() {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		for (int num = 2; num <= MAX_N; num++) {
			if (isPrime[num]) {
				for (int x = num + num; x <= MAX_N; x += num) {
					isPrime[x] = false;
				}
			}
		}
	}
}
