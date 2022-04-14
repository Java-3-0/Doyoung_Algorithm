// 22896KB, 264ms

package bj16564;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long INF = 98765432198765L;

	static int N;
	static long K;
	static long[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Long.parseLong(st.nextToken());

		// 수열 입력
		seq = new long[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Long.parseLong(br.readLine());
		}

		// 정답 계산
		long answer = solve();

		// 출력
		System.out.println(answer);

	} // end main

	/** 이분탐색으로 정답 계산 */
	public static long solve() {
		long left = 1L;
		long right = INF;

		while (left < right) {
			long mid = (left + right + 1) / 2;

			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return left;
	}

	/** target이 가능한 답인지 여부를 리턴 */
	public static boolean isPossible(long target) {
		long levelUp = 0L;
		for (long num : seq) {
			if (num < target) {
				levelUp += (target - num);
			}
		}
		
		if (levelUp <= K) {
			return true;
		}
		else {
			return false;
		}
	}
}