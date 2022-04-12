// 11840KB, 140ms

package bj2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());

		// 투포인터로 정답 계산
		int left = 0;
		int right = 0;
		int sum = 0;

		int answer = 0;

		while (right <= N) {
			if (sum == N) {
				answer++;

				sum += (++right);
				sum -= (left++);
			} else if (sum < N) {
				sum += (++right);
			} else {
				sum -= (left++);
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

}