// 81696KB, 452ms

package bj14627;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_S = (int) 1e6;
	static final int MAX_C = (int) 1e6;
	static final int MAX_L = (int) 1e9;

	static int S, C;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// S, C 입력
		st = new StringTokenizer(br.readLine(), " ");
		S = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[S];
		for (int i = 0; i < S; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 치킨에 넣을 최대 파 길이 계산
		int paChicken = solve();

		long sum = 0L;
		for (int num : seq) {
			sum += (long) num;
		}

		// 라면에 넣을 파 길이 계산
		long paRamen = sum - (long) paChicken * (long) C;

		// 출력
		System.out.println(paRamen);

	} // end main

	public static int solve() {
		int left = 0;
		int right = MAX_L;

		while (left < right) {
			int mid = (left + right + 1) / 2;
			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return left;
	}

	public static boolean isPossible(int len) {
		if (len == 0) {
			return true;
		}

		int cnt = 0;
		for (int num : seq) {
			cnt += (num / len);
		}

		if (cnt >= C) {
			return true;
		} else {
			return false;
		}
	}
}