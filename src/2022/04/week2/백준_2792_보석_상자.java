// 35280KB, 372ms

package bj2792;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e9;
	static final int MAX_M = 3 * (int) 1e6;

	static int N, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[M];
		for (int i = 0; i < M; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 정답 계산
		int answer = getMinJealousy();

		// 출력
		System.out.println(answer);

	} // end main

	public static int getMinJealousy() {
		int left = 1;
		int right = MAX_N;

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

	public static boolean isPossible(int x) {
		int cnt = 0;
		for (int num : seq) {
			cnt += (num / x);
			if (num % x != 0) {
				cnt++;
			}
		}

		if (cnt <= N) {
			return true;
		}

		else {
			return false;
		}
	}
}