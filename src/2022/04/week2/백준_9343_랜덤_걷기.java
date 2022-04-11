package bj9343;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final long MOD = 1000000007L;

	static int N;
	static long[] factorials;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 팩토리얼 미리 계산
		factorials = new long[2 * MAX_N + 1];
		precalcFactorials();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// N 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());

			// (2n)C(n) 계산
			long combi = combination(2 * N, N);

			// 정답 계산
			long answer = modMult(combi, modInv(N + 1));

			// 출력
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	public static long modAdd(long a, long b) {
		return (a + b) % MOD;
	}

	public static long modSub(long a, long b) {
		long ret = MOD + a - b;
		ret %= MOD;
		return ret;
	}

	/** nCr을 계산해서 리턴 */
	public static long combination(int n, int r) {
		return modMult(modMult(factorials[n], modInv(factorials[r])), modInv(factorials[n - r]));
	}

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

	/** 2 * MAX_N까지의 팩토리얼 미리 계산 */
	public static void precalcFactorials() {
		factorials[0] = 1;
		for (int i = 1; i <= 2 * MAX_N; i++) {
			factorials[i] = (factorials[i - 1] * i) % MOD;
		}
	}

}