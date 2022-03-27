// 222940KB, 792ms

package bj1097;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_N = 8;
	static final int MAX_K = 200;

	static int N, K;
	static String[] words;
	static int[] permu;
	static boolean[] isUsed;
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 단어 개수 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		words = new String[N];
		permu = new int[N];
		isUsed = new boolean[N];

		// 단어 입력
		for (int i = 0; i < N; i++) {
			words[i] = br.readLine();
		}

		// K 입력
		K = Integer.parseInt(br.readLine());

		// 모든 순열을 완전탐색하면서 마법의 문자열 개수 갱신
		permutation(0);

		// 마법의 문자열 개수 리턴
		System.out.println(answer);

	} // end main

	/** 모든 순열을 완전탐색하면서 마법의 문자열 개수 갱신 */
	public static void permutation(int cnt) {
		// 순열이 완성된 경우
		if (cnt == N) {
			String p = concat();
			String t = circle(p);

			// t에서 p를 찾은 개수가 K개이면 정답 개수 1 증가
			int found = countPatterns(t.toCharArray(), p.toCharArray());
			if (found == K) {
				answer++;
			}
			
			return;
		}

		// 계속해서 순열을 만들어보는 경우
		for (int i = 0; i < N; i++) {
			if (isUsed[i]) {
				continue;
			}

			isUsed[i] = true;
			permu[cnt] = i;
			permutation(cnt + 1);
			isUsed[i] = false;
		}
	}

	/** s를 원형으로 처리한 문자열을 리턴 */
	public static String circle(String s) {
		if (s.length() == 0) {
			return s;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.append(s);
		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	/** 현재 permu 순서대로 단어들을 이은 문자열을 리턴 */
	public static String concat() {
		StringBuilder sb = new StringBuilder();
		for (int idx : permu) {
			sb.append(words[idx]);
		}

		return sb.toString();
	}

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블을 생성해서 리턴 */
	public static int[] getPrefixTable(char[] arr) {
		int len = arr.length;
		int[] p = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && arr[i] != arr[j]) {
				j = p[j - 1];
			}

			if (arr[i] == arr[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}

	/** KMP 알고리즘을 통해 text에 존재하는 pattern의 개수를 구해서 리턴 */
	public static int countPatterns(char[] text, char[] pattern) {
		int ret = 0;

		int[] p = getPrefixTable(pattern);

		int lenT = text.length;
		int lenP = pattern.length;

		for (int i = 0, j = 0; i < lenT; i++) {
			while (j > 0 && text[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (text[i] == pattern[j]) {
				if (j == lenP - 1) {
					ret++;
					j = p[j];
				} else {
					j++;
				}
			}
		}

		return ret;
	}
}