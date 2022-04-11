// 19204KB, 92ms

package bj16134;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final long MOD = 1000000007L;

	static int N, R;
	static long[] factorials;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, R 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		// 팩토리얼 미리 계산
		factorials = new long[N + 1];
		precalcFactorials();

		// nCr 계산
		long answer = modMult(modMult(factorials[N], modInv(factorials[R])), modInv(factorials[N - R]));

		// 출력
		System.out.println(answer);

	} // end main

	/** a * b의 모듈러 곱셈 수행 */
	public static long modMult(long a, long b) {
		return (a * b) % MOD;
	}

	/** n의 모듈러 곱셈 역원 계산해서 리턴 */
	public static long modInv(long n) {
		return power(n, MOD - 2);
	}

	/** a ^ b 계산해서 리턴 */
	public static long power(long a, long b) {
		if (b == 0) {
			return 1;
		}

		long half = power(a, b / 2);

		long ret = modMult(half, half);
		if (b % 2 != 0) {
			ret = modMult(ret, a);
		}

		return ret;
	}

	/** N까지의 팩토리얼 미리 계산 */
	public static void precalcFactorials() {
		factorials[0] = 1;
		for (int i = 1; i <= N; i++) {
			factorials[i] = (factorials[i - 1] * i) % MOD;
		}
	}

}