// 37880KB, 296ms

package bj20922;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = 100000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 수열 입력
		int[] seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 투포인터
		int left = 0;
		int right = 0;
		int maxLen = 0;
		int[] counts = new int[MAX_NUM + 1];
		counts[seq[0]]++;

		while (right < N) {
			if (counts[seq[right]] <= K) {
				int len = right - left + 1;
				maxLen = Math.max(maxLen, len);

				if (right == N - 1) {
					break;
				}
				counts[seq[++right]]++;
			} else {
				counts[seq[left++]]--;
			}
		}
		
		System.out.println(maxLen);

	} // end main

}