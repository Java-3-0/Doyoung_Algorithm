// 11504KB, 80ms

package bj1072;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long FAIL = -1L;
	static final long INF = 9876543219876543L;

	static long X, Y, Z;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		X = Long.parseLong(st.nextToken());
		Y = Long.parseLong(st.nextToken());
		Z = 100L * Y / X;

		long answer = solve();
		
		System.out.println(answer);

	} // end main

	private static long solve() {
		if (Z >= 99L) {
			return FAIL;
		}

		long left = 1L;
		long right = INF;

		while (left < right) {
			long mid = (left + right) / 2L;

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	public static boolean isPossible(long n) {
		long newZ = 100L * (Y + n) / (X + n);
		
		if (Z < newZ) {
			return true;
		}

		return false;
	}
}