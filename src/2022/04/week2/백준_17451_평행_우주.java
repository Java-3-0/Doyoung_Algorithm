// 57976KB, 580ms

package bj17451;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long INF = 98765432198765432L;

	static int N;
	static long[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

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
			long mid = (left + right) / 2L;
			if (isPossible(mid)) {
				right = mid;
			}
			else {
				left = mid + 1L;
			}
		}
		return left;
	}

	public static boolean isPossible(long speed) {
		for (long num : seq) {
			if (speed < num) {
				return false;
			}

			speed = (speed / num) * num;
		}

		return true;
	}

}