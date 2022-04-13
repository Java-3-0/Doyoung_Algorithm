// 14476KB, 112ms

package bj11561;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MAX_N = (long) 1e16;

	static long MAX_SOL = 2L * (long) 1e8;
	static long N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// M, N 입력
			N = Long.parseLong(br.readLine());

			// 정답 계산
			long answer = solve();

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	public static long solve() {
		long left = 1L;
		long right = MAX_SOL;

		while (left < right) {
			long mid = (left + right + 1L) / 2L;
			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return left;
	}

	public static boolean isPossible(long x) {
		if (x * (x + 1L) / 2L <= N) {
			return true;
		}
		else {
			return false;
		}
	}
}