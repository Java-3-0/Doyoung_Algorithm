// 11444KB, 76ms

package bj9342;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			char[] text = br.readLine().toCharArray();
			if (followsRule(text)) {
				sb.append("Infected!").append("\n");
			} else {
				sb.append("Good").append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	public static boolean followsRule(char[] text) {
		int len = text.length;

		if (text[0] < 'A' || text[0] > 'F') {
			return false;
		}
		if (text[len - 1] < 'A' || text[len - 1] > 'F') {
			return false;
		}

		// 0번 인덱스부터 시작해보기
		int idx = 0;
		boolean foundA = false;
		boolean foundF = false;
		boolean foundC = false;

		while (idx < len && text[idx] == 'A') {
			foundA = true;
			idx++;
		}

		while (idx < len && text[idx] == 'F') {
			foundF = true;
			idx++;
		}

		while (idx < len && text[idx] == 'C') {
			foundC = true;
			idx++;
		}

		if (foundA && foundF && foundC && (idx == len || idx == len - 1)) {
			return true;
		}

		// 1번 인덱스부터 시작해보기
		idx = 1;

		foundA = false;
		foundF = false;
		foundC = false;

		while (idx < len && text[idx] == 'A') {
			foundA = true;
			idx++;
		}

		while (idx < len && text[idx] == 'F') {
			foundF = true;
			idx++;
		}

		while (idx < len && text[idx] == 'C') {
			foundC = true;
			idx++;
		}

		if (foundA && foundF && foundC && (idx == len || idx == len - 1)) {
			return true;
		}

		return false;
	}
}