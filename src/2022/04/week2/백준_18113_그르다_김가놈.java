// 76536KB, 436ms

package bj18113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_L = (int) 1e9;
	static int N, K, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		seq = new int[N];
		for (int i = 0; i < N; i++) {
			int input = Integer.parseInt(br.readLine());
			if (input >= 2 * K) {
				input -= 2 * K;
			} else if (input > K) {
				input -= K;
			} else {
				input = 0;
			}

			seq[i] = input;
		}
		
		int answer = solve();
		
		if (answer == 0) {
			System.out.println(-1);
		}
		
		else {
			System.out.println(answer);
		}

	} // end main

	public static int solve() {
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

	public static boolean isPossible(int P) {
		if (P == 0) {
			return true;
		}

		int cnt = 0;
		for (int num : seq) {
			cnt += num / P;
		}

		if (cnt >= M) {
			return true;
		} else {
			return false;
		}
	}

}