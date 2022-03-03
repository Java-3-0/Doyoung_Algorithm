// 39172KB, 236ms

package bj1593;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int ALPHABETS = 26;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		br.readLine();
		char[] W = br.readLine().toCharArray();
		char[] S = br.readLine().toCharArray();

		// 계산
		int answer = solve(S, W);

		// 출력
		System.out.println(answer);

	} // end main

	/** S에 등장하는 W의 순열의 개수를 리턴 */
	public static int solve(char[] S, char[] W) {
		int lenW = W.length;
		int lenS = S.length;

		// W에 각 알파벳이 몇 번씩 등장하는지 센다
		int[] targetCounts = new int[2 * ALPHABETS];
		for (char c : W) {
			targetCounts[charToInt(c)]++;
		}

		// S에서도 W길이만큼씩 보면서 그 부분의 알파벳을 카운트한다
		int[] counts = new int[2 * ALPHABETS];
		int ret = 0;

		// 초기에 W 길이만큼 처리
		for (int i = 0; i < lenW; i++) {
			counts[charToInt(S[i])]++;
		}
		if (Arrays.equals(counts, targetCounts)) {
			ret++;
		}

		// 나머지 부분에 대해 슬라이딩 윈도우 방식으로 처리
		for (int i = lenW; i < lenS; i++) {
			counts[charToInt(S[i])]++;
			counts[charToInt(S[i - lenW])]--;

			if (Arrays.equals(counts, targetCounts)) {
				ret++;
			}
		}

		return ret;
	}

	/** c를 0 ~ (26 * 2 - 1)사이의 int 값으로 변환 */
	public static int charToInt(char c) {
		if (c >= 'a') {
			return c - 'a';
		} else {
			return c - 'A' + ALPHABETS;
		}
	}
}