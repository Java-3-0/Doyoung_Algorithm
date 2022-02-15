// 11540KB, 76ms

package bj2941;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int answer = countCroatian(s);
		System.out.println(answer);
	}

	/** 문자열에 존재하는 크로아티아 알파벳의 개수를 리턴 */
	public static int countCroatian(String s) {
		int length = s.length();

		int count = length;

		// 2글자 -> 1글자가 되는 경우를 처리
		for (int i = 1; i < length; i++) {
			char c = s.charAt(i);
			char prev = s.charAt(i - 1);

			switch (c) {
			case '=':
				if (prev == 'z' || prev == 'c' || prev == 's') {
					count--;
				}
				break;
			case '-':
				if (prev == 'c' || prev == 'd') {
					count--;
				}
				break;
			case 'j':
				if (prev == 'n' || prev == 'l') {
					count--;
				}
				break;
			default:
				break;
			}
		}
		
		// 3글자 -> 1글자가 되는 경우를 추가로 처리
		for (int i = 2; i < length; i++) {
			char c = s.charAt(i);
			char prev = s.charAt(i - 1);
			char twoPrev = s.charAt(i - 2);

			switch (c) {
			case '=':
				if (prev == 'z' && twoPrev == 'd') {
					count--; // 2글자인 걸 처리할 때 이미 -1 했으므로 -1만 추가로 더 해준다.
				}
				break;
			default:
				break;
			}
		}

		return count;
	}
}
