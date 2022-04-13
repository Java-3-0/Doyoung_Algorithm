// 15924KB, 148ms

package bj13702;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000, MAX_K = 1000000;
	static final long INF = 98765432198765432L;

	static int N, K;
	static long[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new long[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Long.parseLong(br.readLine());
		}

		long answer = solve();

		System.out.println(answer);

	} // end main

	public static long solve() {
		long left = 0;
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

	public static boolean isPossible(long x) {
		if (x == 0) {
			return true;
		}

		int cnt = 0;
		for (long num : seq) {
			cnt += (num / x);
		}

		if (cnt >= K) {
			return true;
		} else {
			return false;
		}
	}

}