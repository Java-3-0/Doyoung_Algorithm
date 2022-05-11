// 11916KB, 128ms

package bj2622;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		// i >= j >= k로 만든다
		long answer = 0L;
		for (int i = N / 2; i >= N / 3; i--) {
			for (int j = i; j >= 1; j--) {
				int k = N - i - j;
				if (k <= 0 || k > j) {
					break;
				}
				else {
					answer++;
				}
			}
		}

		System.out.println(answer);

	} // end main
}