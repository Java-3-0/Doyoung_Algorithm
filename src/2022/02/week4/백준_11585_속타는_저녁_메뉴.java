// 180424KB, 580ms

package bj11585;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 룰렛 길이 입력
		int N = Integer.parseInt(br.readLine());

		// 고기를 먹게 되는 패턴 입력
		st = new StringTokenizer(br.readLine(), " ");
		char[] pattern = new char[N];
		for (int i = 0; i < N; i++) {
			pattern[i] = st.nextToken().charAt(0);
		}

		// 현재의 원형 룰렛 모양 입력받아서 원형 대신 사이즈 2배 - 1의 선형으로 나타냄.
		st = new StringTokenizer(br.readLine(), " ");
		char[] textTwice = new char[2 * N - 1];
		for (int i = 0; i < N - 1; i++) {
			char input = st.nextToken().charAt(0);
			textTwice[i] = input;
			textTwice[N + i] = input;
		}
		textTwice[N - 1] = st.nextToken().charAt(0);

		// KMP알고리즘을 이용해서 문자열에서 패턴 탐색
		List<Integer> foundIdx = searchByKMP(textTwice, pattern);

		// 패턴 찾은 횟수 / 전체 문자열 길이가 당첨 확률
		int numer = foundIdx.size();
		int denom = N;

		// 분수를 기약분수 형태로 변경
		int gcd = gcd(numer, denom);
		numer /= gcd;
		denom /= gcd;
		
		// 결과 출력
		System.out.printf("%d/%d\n", numer, denom);
		
	} // end main

	/** 두 수의 최대공약수를 리턴 */
	public static int gcd(int a, int b) {
		if (a == 0) {
			return b;
		}

		return gcd(b % a, a);
	}

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
	public static List<Integer> searchByKMP(char[] text, char[] pattern) {
		List<Integer> ret = new ArrayList<>();

		int[] p = getPrefixTable(pattern);

		int lenT = text.length;
		int lenP = pattern.length;
		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					int foundIdx = i - j;
					ret.add(foundIdx);
					j = p[j];
				} else {
					j++;
				}
			}
		}

		return ret;
	}
}