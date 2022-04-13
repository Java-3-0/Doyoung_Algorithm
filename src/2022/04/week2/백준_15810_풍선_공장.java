// 125304KB, 808ms

package bj15810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long INF = (long) (1e12 + 1);

	static int N;
	static long M;
	static long[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Long.parseLong(st.nextToken());

		seq = new long[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		long answer = solve();

		System.out.println(answer);

	} // end main

	public static long solve() {
		long left = 0L;
		long right = INF;

		while (left < right) {
			long mid = (left + right) / 2;

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	public static boolean isPossible(long x) {
		if (x == 0L) {
			return false;
		}

		long cnt = 0L;
		for (long num : seq) {
			cnt += (x / num);
			if (cnt < 0) {
				return true;
			}
		}

		if (cnt >= M) {
			return true;
		} else {
			return false;
		}
	}

}