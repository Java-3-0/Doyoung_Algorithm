// 11488KB, 76ms

package bj9660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long N = Long.parseLong(br.readLine());

		String answer = canWin(N) ? "SK" : "CY";

		System.out.println(answer);

	} // end main

	public static boolean canWin(long n) {
		int remainder = (int) (n % 7L);

		if (remainder == 0 || remainder == 2) {
			return false;
		}

		return true;

	}

}