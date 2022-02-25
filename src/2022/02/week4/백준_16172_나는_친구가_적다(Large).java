// 19988KB, 172ms

package bj16172;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		String S = br.readLine();
		String P = br.readLine();
		
		char[] arrS = S.toCharArray();
		int lenS = arrS.length;
		
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < lenS; i++) {
			char c = arrS[i];
			if ('0' <= c && c <= '9') {
				continue;
			}
			tmp.append(c);
		}
		
		char[] text = tmp.toString().toCharArray();
		char[] pattern = P.toCharArray();
		
		boolean isFound = searchByKMP(text, pattern);

		int answer = isFound ? 1 : 0;

		System.out.println(answer);

	} // end main

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블 생성 */
	public static int[] getPrefixTable(char[] pattern) {
		int len = pattern.length;
		int p[] = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && pattern[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (pattern[i] == pattern[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}

	/** KMP 알고리즘을 이용하여 text에서 pattern을 찾아 찾은 위치들을 리스트 형태로 리턴 */
	public static boolean searchByKMP(char[] text, char[] pattern) {
		int[] p = getPrefixTable(pattern);

		int lenT = text.length;
		int lenP = pattern.length;
		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					j = p[j];
					return true;
				} else {
					j++;
				}
			}
		}

		return false;
	}
}