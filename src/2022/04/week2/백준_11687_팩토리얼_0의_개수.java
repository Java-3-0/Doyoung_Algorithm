// 11500KB, 76ms

package bj11687;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static final int INF = 987654321;
	static final int FAIL = -1;
	static final int MAX_M = (int) 1e8;

	static int M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		M = Integer.parseInt(br.readLine());

		int answer = solve();

		System.out.println(answer);

	} // end main

	public static int solve() {
		int left = 0;
		int right = INF;

		while (left < right) {
			int mid = (left + right) / 2;
			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		if (countFactorialZeroes(right) == M) {
			return right;
		} else {
			return FAIL;
		}

	}

	public static boolean isPossible(int n) {
		if (countFactorialZeroes(n) >= M) {
			return true;
		}
		return false;
	}

	/** n!에서 끝의 0 개수를 리턴 */
	public static int countFactorialZeroes(int n) {
		int ret = 0;
		while (n > 0) {
			n /= 5;
			ret += n;
		}

		return ret;
	}
}