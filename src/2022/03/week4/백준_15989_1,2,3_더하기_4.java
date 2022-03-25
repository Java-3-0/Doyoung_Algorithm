// 12940KB, 104ms

package bj15989;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			int N = Integer.parseInt(br.readLine());
			int answer = 0;
			// 3의 개수를 하나씩 늘려가면서, 남은 수에서 존재할 수 있는 2의 개수 경우의 수를 센다.
			while (N >= 0) {
				answer += (1 + N / 2);
				N -= 3;
			}

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}