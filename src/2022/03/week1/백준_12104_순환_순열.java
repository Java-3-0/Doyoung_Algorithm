// 19720KB, 176ms

package bj12104;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 문자열 입력
		String t = br.readLine();
		String p = br.readLine();
		
		// t 문자열을 원형으로 나타내기 위해 2 * lenT - 1길이의 선형으로 표현한다.
		int lenT = t.length();
		char[] text = new char[2 * lenT - 1];
		char[] pattern = p.toCharArray();
		
		for (int i = 0; i < lenT - 1; i++) {
			text[i] = t.charAt(i);
			text[lenT + i] = t.charAt(i);
		}
		text[lenT - 1] = t.charAt(lenT - 1);

		// KMP 알고리즘을 이용하여 text에서 pattern을 찾는다.
		List<Integer> found = search(text, pattern);
		
		// 찾은 개수가 순환 순열의 개수
		int answer = found.size();
		
		// 출력
		System.out.println(answer);
	
	} // end main

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블 생성해서 리턴 */
	public static int[] getPrefixTable(char[] pattern) {
		int len = pattern.length;
		int[] p = new int[len];
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
	
	/** KMP 알고리즘을 이용하여 text에서 pattern을 찾은 위치들을 리스트 형태로 리턴 */
	public static List<Integer> search(char[] text, char[] pattern) {
		int lenT = text.length;
		int lenP = pattern.length;

		int[] p = getPrefixTable(pattern);
		
		List<Integer> ret = new ArrayList<>();

		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					ret.add(i - j);
					j = p[j];
				}
				else {
					j++;
				}
			}
		}

		return ret;
	}
}