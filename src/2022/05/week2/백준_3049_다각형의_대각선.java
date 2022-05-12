// 11512KB, 72ms

package bj3049;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		
		int answer = 0;
		for (int i = 0; i  < N; i++) {
			for (int j = i + 1; j < N; j++) {
				int points1 = j - i - 1;
				int points2 = N - 2 - points1;
				answer += points1 * points2;
			}
		}

		answer /= 2;
		System.out.println(answer);
	} // end main
}