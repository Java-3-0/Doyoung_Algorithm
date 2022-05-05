package bj16189;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		char[] line = br.readLine().toCharArray();
		long K = Long.parseLong(br.readLine());

		if (isPalindrome(line)) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
		
	} // end main

	public static boolean isPalindrome(char[] line) {
		int len = line.length;
		for (int i = 0; i < len / 2; i++) {
			if (line[i] != line[len - 1 - i]) {
				return false;
			}
		}

		return true;
	}
}