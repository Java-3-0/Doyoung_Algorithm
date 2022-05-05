// 11540KB, 80ms

package bj1475;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static final int DIGITS = 10;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int[] counts = new int[DIGITS];

		String line = br.readLine();
		for (char c : line.toCharArray()) {
			int num = c - '0';
			counts[num]++;
		}

		int maxCnt = 0;
		for (int i = 0; i <= 8; i++) {
			int cnt = counts[i];
			if (i == 6) {
				cnt = (int) Math.ceil(((double) (counts[6] + counts[9]) / 2.0));
			}

			maxCnt = maxCnt < cnt ? cnt : maxCnt;
		}

		System.out.println(maxCnt);

	} // end main

}