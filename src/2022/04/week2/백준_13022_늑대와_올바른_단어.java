// 11484KB, 76ms

package bj13022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();
		
		if (isValid(s)) {
			System.out.println(1);
		}
		else {
			System.out.println(0);
		}

	} // end main

	public static boolean isValid(String s) {
		int len = s.length();
		int idx = 0;
		while (idx < len) {
			// w의 개수 세기
			int cnt = 0;			
			while (idx < len && s.charAt(idx) == 'w') {
				cnt++;
				idx++;
			}
			
			if (cnt == 0) {
				return false;
			}
			
			// o 찾기
			for (int i = 0; i < cnt; i++) {
				if (idx >= len || s.charAt(idx) != 'o') {
					return false;
				}

				idx++;
			}

			// l 찾기
			for (int i = 0; i < cnt; i++) {
				if (idx >= len || s.charAt(idx) != 'l') {
					return false;
				}

				idx++;
			}

			// f 찾기
			for (int i = 0; i < cnt; i++) {
				if (idx >= len || s.charAt(idx) != 'f') {
					return false;
				}

				idx++;
			}

		}

		return true;
	}

}