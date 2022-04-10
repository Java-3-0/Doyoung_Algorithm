// 11760KB, 92ms

package bj1942;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_TIME = 235959;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= 3; tc++) {
			st = new StringTokenizer(br.readLine(), ": ");
			int startTime = 0;
			startTime += 10000 * Integer.parseInt(st.nextToken());
			startTime += 100 * Integer.parseInt(st.nextToken());
			startTime += Integer.parseInt(st.nextToken());

			int endTime = 0;
			endTime += 10000 * Integer.parseInt(st.nextToken());
			endTime += 100 * Integer.parseInt(st.nextToken());
			endTime += Integer.parseInt(st.nextToken());

			int answer = countMultiplesOf3(startTime, endTime);
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static int countMultiplesOf3(int startTime, int endTime) {
		int ret = 0;
		if (startTime <= endTime) {
			for (int num = startTime; num <= endTime; num++) {
				if (isClockNumber(num) && num % 3 == 0) {
					ret++;
				}
			}
		}

		else {
			for (int num = startTime; num <= MAX_TIME; num++) {
				if (isClockNumber(num) && num % 3 == 0) {
					ret++;
				}
			}
			for (int num = 0; num <= endTime; num++) {
				if (isClockNumber(num) && num % 3 == 0) {
					ret++;
				}
			}
		}

		return ret;
	}

	public static boolean isClockNumber(int n) {
		int hour = n / 10000;
		n %= 10000;
		int min = n / 100;
		n %= 100;
		int sec = n;

		if (0 <= hour && hour < 24 && 0 <= min && min < 60 && 0 <= sec && sec < 60) {
			return true;
		}

		return false;
	}

}