// 16565KB, 124ms

package bj1958;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		char[] text1 = ("0" + br.readLine()).toCharArray();
		char[] text2 = ("0" + br.readLine()).toCharArray();
		char[] text3 = ("0" + br.readLine()).toCharArray();

		// 계산
		int answer = getLengthOfLCS(text1, text2, text3);

		// 출력
		System.out.println(answer);

	} // end main

	/** LCS의 최대 길이를 구해서 리턴 */
	public static int getLengthOfLCS(char[] text1, char[] text2, char[] text3) {
		int len1 = text1.length;
		int len2 = text2.length;
		int len3 = text3.length;

		// dp[i][j][k]는 text1에서 i번 인덱스, text2에서 j번 인덱스, text3에서 k번 인덱스까지 고려한 최장 공통 부분
		// subsequence 길이
		int[][][] dp = new int[len1][len2][len3];

		// dp 수행
		int ret = 0;
		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				for (int k = 0; k < len3; k++) {
					if (i == 0 || j == 0 || k == 0) {
						dp[i][j][k] = 0;
						continue;
					}

					if (text1[i] == text2[j] && text2[j] == text3[k]) {
						dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
						ret = Math.max(ret, dp[i][j][k]);
					} else {
						int tmp = Math.max(dp[i - 1][j][k], dp[i][j - 1][k]);
						tmp = Math.max(tmp, dp[i][j][k - 1]);
						dp[i][j][k] = tmp;
					}
				}
			}
		}

		return ret;
	}

}