// 69476KB, 308ms

package bj9421;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력
		int N = Integer.parseInt(br.readLine());

		// N 이하의 모든 수에 대해 소수 여부를 미리 계산
		boolean[] isPrime = eratosthenes(N);

		// 1은 소수가 아니고, 2는 상근수가 아니므로 3부터 시작하고, 홀수만 탐색
		for (int num = 3; num <= N; num += 2) {
			if (isPrime[num] && isSangGeun(num)) {
				sb.append(num).append("\n");
			}
		}

		System.out.print(sb.toString());
	}

	/** N까지의 모든 수에 대해 에레토스테네스의 체를 수행한 결과를 리턴 */
	public static boolean[] eratosthenes(int N) {
		// isPrime 배열 초기화
		boolean[] isPrime = new boolean[N + 1];
		Arrays.fill(isPrime, true);
		// 0과 1은 소수가 아니다
		isPrime[0] = false;
		isPrime[1] = false;

		// 2이상의 모든 p에 대해서, p가 소수라면 p의 배수들을 모두 지운다.
		for (int p = 2; p <= N; p++) {
			if (!isPrime[p]) {
				continue;
			}

			for (int num = p + p; num <= N; num += p) {
				isPrime[num] = false;
			}
		}

		return isPrime;
	}

	/** 정수 n이 상근수인지 여부를 리턴 */
	public static boolean isSangGeun(int n) {
		Set<Integer> visited = new HashSet<>();

		// 같은 수가 다시 나올 때까지 반복
		do {
			// n의 방문 여부를 갱신
			visited.add(n);
			// 1이 나오면 상근수이다.
			if (n == 1) {
				return true;
			}
			// n을 갱신
			n = digitSquareSum(n);
		} while (!visited.contains(n));

		// 1을 찾지 못하고, 같은 수를 다시 만나서 루프를 빠져나왔다면, 상근수가 아니다.
		return false;
	}

	/** 정수 n의 각 자리수 제곱의 합을 리턴 */
	public static int digitSquareSum(int n) {
		int ret = 0;

		while (n > 0) {
			ret += (n % 10) * (n % 10);
			n /= 10;
		}

		return ret;
	}
}