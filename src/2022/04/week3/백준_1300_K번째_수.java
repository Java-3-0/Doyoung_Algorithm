// 11548KB, 124ms

package bj1300;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_N = (int) 1e5, MAX_K = (int) 1e9;

	static long N, K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N, K 입력
		N = Long.parseLong(br.readLine());
		K = Long.parseLong(br.readLine());

		// 정답 계산
		long answer = solve();

		// 출력
		System.out.println(answer);

	} // end main

	/** 정답을 계산해서 리턴 */
	public static long solve() {
		long left = 1;
		long right = N * N;

		while (left < right) {
			long mid = (left + right) / 2L;

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1L;
			}
		}

		return right;
	}

	/** x이하의 수가 k개 이상이면 true, 아니면 false를 리턴 */
	public static boolean isPossible(long x) {
		long cnt = 0L;
		for (long i = 1L; i <= N; i++) {
			cnt += Math.min(N, x / i);
		}

		if (K <= cnt) {
			return true;
		}

		return false;
	}

}