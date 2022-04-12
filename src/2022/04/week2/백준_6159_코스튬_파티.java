// 16040KB, 228ms

package bj6159;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, S 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		// 수열 입력
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 수열 오름차순 정렬
		Arrays.sort(seq);

		// 투포인터
		int left = 0;
		int right = N - 1;
		int answer = 0;

		while (left < N - 1) {
			int sum = seq[left] + seq[right];

			if (sum <= S) {
				answer += (right - left);
				left++;
				right = N - 1;
			} else {
				right--;
				
				if (right <= left) {
					break;
				}
			}

		}

		System.out.println(answer);

	} // end main

}