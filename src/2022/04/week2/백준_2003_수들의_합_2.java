// 12700KB, 108ms

package bj2003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 투포인터로 정답 계산
		int left = 0;
		int right = 0;
		int sum = seq[0];

		int answer = 0;

		while (right < N) {
			if (sum == M) {
				answer++;
				if (right == N - 1) {
					break;
				}

				sum += seq[++right];
				sum -= seq[left++];

			} else if (sum < M) {
				if (right == N - 1) {
					break;
				}

				sum += seq[++right];
			} else {
				sum -= seq[left++];
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

}