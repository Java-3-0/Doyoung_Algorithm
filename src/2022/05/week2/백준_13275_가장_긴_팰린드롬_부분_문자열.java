package bj13275;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 문자열 입력받고 문자 양옆에 '|'를 붙인 새로운 문자열을 만든다.
		String input = br.readLine();
		for (int i = 0; i < input.length(); i++) {
			sb.append('|');
			sb.append(input.charAt(i));
		}
		sb.append('|');

		char[] text = sb.toString().toCharArray();

		// Manacher 알고리즘으로 각 위치에서의 팰린드롬 최대 길이를 계산한다
		int[] palindromes = manacher(text);

		// 모든 중심 위치에서의 팰린드롬 최대 길이 중 최대값을 출력한다
		int answer = 0;
		for (int num : palindromes) {
			answer = Math.max(answer, num);
		}
		System.out.println(answer);

	} // end main

	/** Manacher 알고리즘으로 문자열의 각 위치를 중심으로 한 팰린드롬의 최대 길이를 구해서 배열로 리턴한다 */
	public static int[] manacher(char[] text) {
		int len = text.length;

		// ret[i]는 i번 인덱스를 중심으로 한 팰린드롬의 최대 길이
		int[] ret = new int[len];

		// p는 j < i인 j 중에서 (j + ret[j])가 최대가 되는 j의 값 (팰린드롬의 중심점)
		int p = 0;

		// r는 이미 팰린드롬을 구한 구간의 오른쪽 끝 인덱스
		int r = 0;

		// Manacher 알고리즘 수행
		for (int i = 0; i < len; i++) {
			// ret[i]의 초기값 결정
			if (r < i) {
				ret[i] = 0;
			} else {
				ret[i] = Math.min(ret[2 * p - i], r - i);
			}

			// text[i - ret[i] - 1]와 text[i + ret[i] + 1]가 같은 동안 A[i]를 증가시킨다
			while (0 <= i - ret[i] - 1 && i + ret[i] + 1 < len && text[i - ret[i] - 1] == text[i + ret[i] + 1]) {
				ret[i]++;
			}

			// r, p값 갱신
			if (r < i + ret[i]) {
				r = i + ret[i];
				p = i;
			}
		}

		return ret;
	}
}