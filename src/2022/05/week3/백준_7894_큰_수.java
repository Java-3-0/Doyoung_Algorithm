// 11752KB, 960ms

package bj7894;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			int N = Integer.parseInt(br.readLine());

			// LOG AB = LOG A + LOG B 임을 이용해서 곱의 로그값을 합연산으로 구한다
			double log = 0;
			for (int num = 1; num <= N; num++) {
				log += Math.log10(num);
			}

			// 그 자리수를 출력한다
			long answer = (long) Math.floor(log) + 1L;

			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main
}