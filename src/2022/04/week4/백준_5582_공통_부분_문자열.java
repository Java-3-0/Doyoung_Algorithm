package bj5582;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		String line1 = "0" + br.readLine();
		String line2 = "0" + br.readLine();

		// 계산
		int answer = getLengthOfLCS(line1.toCharArray(), line2.toCharArray());

		// 출력
		System.out.println(answer);

	} // end main

	/** LCS의 최대 길이를 구해서 리턴 */
	public static int getLengthOfLCS(char[] text1, char[] text2) {
		int len1 = text1.length;
		int len2 = text2.length;

		// dp[i][j]는 text1에서 i번 인덱스, text2에서 j번 인덱스에서 끝나는 최장 공통 부분 문자열 길이
		int[][] dp = new int[len1 + 1][len2 + 1];

		// dp 수행
		int ret = 0;
		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
					continue;
				}

				if (text1[i] == text2[j]) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
					ret = Math.max(ret, dp[i][j]);
				}
			}
		}

		return ret;
	}

}