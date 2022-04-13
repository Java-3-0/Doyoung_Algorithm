// 162384KB, 536ms

package bj16401;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_M = (int) 1e6;
	static final int MAX_N = (int) 1e6;
	static final int MAX_L = (int) 1e9;

	static int M, N;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// M, N 입력
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		int answer = getMaxLength();
		
		System.out.println(answer);
		
	} // end main

	public static int getMaxLength() {
		int left = 0;
		int right = MAX_L;
		
		while (left < right) {
			int mid = (left + right + 1) / 2;
			if (isPossible(mid)) {
				left = mid;
			}
			else {
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

		if (cnt >= M) {
			return true;
		} else {
			return false;
		}
	}
}