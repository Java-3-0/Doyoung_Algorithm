// 25980KB, 292ms

package bj2230;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 수열 입력
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 수열 오름차순 정렬
		Arrays.sort(seq);

		// 투포인터
		int left = 0;
		int right = 1;
		int minDiff = Integer.MAX_VALUE;

		while (left < N - 1 && right < N) {
			if (left == right) {
				right++;
				continue;
			}

			int diff = seq[right] - seq[left];
			if (diff >= M) {
				minDiff = diff < minDiff ? diff : minDiff;
				left++;
			} else {
				right++;
			}
		}

		System.out.println(minDiff);

	} // end main

}