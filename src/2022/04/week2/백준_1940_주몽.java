// 15064KB, 140ms

package bj1940;

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
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());

		// 수열 입력
		int[] seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 수열 오름차순 정렬
		Arrays.sort(seq);

		int left = 0;
		int right = N - 1;

		int answer = 0;
		while (left < right) {
			int sum = seq[left] + seq[right];

			if (sum == M) {
				answer++;
				left++;
				right--;
			} else if (sum < M) {
				left++;
			} else {
				right--;
			}
		}

		System.out.println(answer);

	} // end main

}