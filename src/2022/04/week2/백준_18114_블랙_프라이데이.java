// 13840KB, 408ms

package bj18114;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N, C;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(seq);
		
		int answer = solve();
		
		System.out.println(answer);

	} // end main

	public static int solve() {
		for (int i = 0; i < N; i++) {
			// 1개 물건 선택
			if (seq[i] == C) {
				return 1;
			}

			for (int j = i + 1; j < N; j++) {
				// 2개 물건 선택
				int sum = seq[i] + seq[j];
				if (sum == C) {
					return 1;
				}
				
				// 3개 물건 선택
				int target = C - sum;
				if (target < 0) {
					break;
				}
				
				if (exist(j + 1, target)) {
					return 1;
				}
				
			} // end for j
			
		} // end for i
		
		return 0;
	}
	
	public static boolean exist (int leftIdx, int target) {
		int left = leftIdx;
		int right = N - 1;
		
		if (left > right) {
			return false;
		}
		
		while (left < right) {
			int mid = (left + right) / 2;
			
			if (seq[mid] < target) {
				left = mid + 1;
			}
			else {
				right = mid;
			}
		}
		
		if (seq[right] == target) {
			return true;
		}
		else {
			return false;
		}
	}

}