// 13020KB, 112ms

package bj2512;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000;
	static final int MAX_M = (int) 1e9;

	static int N, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		M = Integer.parseInt(br.readLine());

		int answer = solve();

		System.out.println(answer);

	} // end main

	public static int solve() {
		int left = 0;
		int right = findMax(seq);

		while (left < right) {
			int mid = (left + right + 1) / 2;
			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return right;
	}

	public static int findMax(int[] arr) {
		int ret = 0;
		for (int num : arr) {
			if (ret < num) {
				ret = num;
			}
		}

		return ret;
	}

	public static boolean isPossible(int money) {
		int sum = 0;
		for (int num : seq) {
			sum += Math.min(num, money);
			if (sum < 0) {
				return false;
			}
		}

		if (sum <= M) {
			return true;
		} else {
			return false;
		}
	}

}