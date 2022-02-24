package bj12927;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	static final int MAX_N = 1000;
	
	/** 각 전구마다 켜졌으면 true, 꺼졌으면 false를 담는 배열 */
	static final boolean[] isTurnedOn = new boolean[MAX_N + 1];
	
	static int len;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = br.readLine();
		len = line.length();
		for (int i = 1; i <= len; i++) {
			isTurnedOn[i] = line.charAt(i - 1) == 'Y' ? true : false;
		}
		
		
		int count = 0;
		for (int i = 1; i <= len; i++) {
			if (isTurnedOn[i]) {
				clickSwitch(i);
				count++;
			}
		}
		
		System.out.println(count);

	} // end main

	public static void clickSwitch (int switchNum) {
		for (int i = switchNum; i <= len; i += switchNum) {
			isTurnedOn[i] = !isTurnedOn[i];
		}
	}
}