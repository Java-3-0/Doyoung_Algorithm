// 12368KB, 80ms

package bj23306;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// Q1 : 첫 칸의 값은?
		System.out.printf("? %d\n", 1);
		System.out.flush();

		// A1 : 첫 칸의 값
		int first = Integer.parseInt(br.readLine());

		// Q2: 마지막 칸의 값은?
		System.out.printf("? %d\n", N);
		System.out.flush();

		// A2: 마지막 칸의 값
		int last = Integer.parseInt(br.readLine());

		// 정답 계산
		int answer = 0;
		if (first < last) {
			answer = 1;
		} else if (first > last) {
			answer = -1;
		}

		// 정답 출력
		System.out.printf("! %d\n", answer);
		System.out.flush();

	} // end main

}