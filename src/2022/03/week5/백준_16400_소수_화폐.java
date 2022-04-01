// 12508KB, 292ms

package bj16400;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	static final int MAX_N = 40000;
	static final int MOD = 123456789;

	static List<Integer> primes = new ArrayList<>();
	static int N;

	// dp[n] = n원을 만드는 방법의 수
	static int dp[] = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 소수들을 미리 계산
		precalcPrimes();
		

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 2만 사용할 때의 상태로 dp 초기화
		for (int i = 0; i <= N; i += 2) {
			dp[i] = 1;
		}

		// 3부터의 소수에 대해 dp 갱신
		int size = primes.size();
		for (int i = 1; i < size; i++) {
			int prime = primes.get(i);

			for (int n = prime; n <= N; n++) {
				dp[n] = (dp[n] + dp[n - prime]) % MOD;
			}
		}

		// 정답 출력
		int answer = dp[N];

		System.out.println(answer);

	} // end main

	/** MAX_N 이하의 소수들을 모두 계산해서 primes에 담는다 */
	public static void precalcPrimes() {
		boolean[] isPrime = new boolean[MAX_N + 1];
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;
		for (int i = 2; i <= MAX_N; i++) {
			for (int k = i + i; k <= MAX_N; k += i) {
				isPrime[k] = false;
			}
		}

		for (int i = 0; i <= MAX_N; i++) {
			if (isPrime[i]) {
				primes.add(i);
			}
		}

		return;
	}

}