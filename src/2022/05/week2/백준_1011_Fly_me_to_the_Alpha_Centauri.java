// 11640KB, 80ms

package bj1011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x = Integer.parseInt(st.nextToken());
			long y = Integer.parseInt(st.nextToken());

			long moves = y - x;

			long left = 1L;
			long right = (1L << 31);

			while (left < right) {
				long mid = (left + right) / 2L;

				if (isPossible(mid, moves)) {
					right = mid;
				} else {
					left = mid + 1;
				}
			}

			sb.append(left).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static boolean isPossible(long k, long moves) {
		long k1 = k / 2;
		long k2 = k - k1;

		long possibleMoves = (k1 * (k1 + 1L) / 2L) + (k2 * (k2 + 1L) / 2L);

		if (moves <= possibleMoves) {
			return true;
		}
		return false;
	}

}