// 11608KB, 76ms

package bj1508;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final int MAX_K = 50;
	static final int MAX_M = MAX_K;

	static int N, M, K;
	static int[] positions;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 심판 위치 입력
		positions = new int[K];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			positions[i] = Integer.parseInt(st.nextToken());
		}

		int left = 0;
		int right = MAX_N;

		while (left < right) {
			int mid = (left + right + 1) / 2;

			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		String answer = getAnswer(left);

		System.out.println(answer);

	} // end main

	public static boolean isPossible(int dist) {
		int cnt = 1;
		int prev = positions[0];

		for (int i = 1; i < K; i++) {
			int cur = positions[i];
			if (cur - prev >= dist) {
				cnt++;
				prev = cur;
			}
		}

		if (cnt >= M) {
			return true;
		} else {
			return false;
		}
	}

	public static String getAnswer(int dist) {
		StringBuilder sb = new StringBuilder();
		int cnt = 0;

		int prev = positions[0];
		sb.append("1");
		cnt++;
		for (int i = 1; i < K; i++) {
			if (cnt == M) {
				sb.append("0");
			}

			else {
				int cur = positions[i];
				if (cur - prev >= dist) {
					sb.append("1");
					cnt++;

					prev = cur;
				} else {
					sb.append("0");
				}
			}
		}

		return sb.toString();
	}

}