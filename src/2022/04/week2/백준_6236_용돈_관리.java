// 20900KB, 216ms

package bj6236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int INF = (int) (1e9 + 1e5);

	static int N, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		int answer = getMinK();

		System.out.println(answer);

	} // end main

	public static int getMinK() {
		int left = 1;
		int right = INF;

		while (left < right) {
			int mid = (left + right) / 2;

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	public static boolean isPossible(int moneyOut) {
		int moneyHave = 0;

		int cnt = 0;
		for (int num : seq) {
			if (moneyOut < num) {
				return false;
			}

			if (moneyHave < num) {
				moneyHave = moneyOut;
				moneyHave -= num;
				cnt++;
			} else {
				moneyHave -= num;
			}
		}

		if (cnt <= M) {
			return true;
		} else {
			return false;
		}

	}
}