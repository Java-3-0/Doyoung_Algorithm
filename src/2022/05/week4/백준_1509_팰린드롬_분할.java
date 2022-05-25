// 37052KB, 224ms

package bj1509;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_LEN = 2500;
	static final int CACHE_EMPTY = -1, FALSE = 0, TRUE = 1;
	static final int INF = 987654321;

	static int[][] palindromeCache = new int[MAX_LEN + 1][MAX_LEN + 1];
	static char[] text = new char[MAX_LEN + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		initCache();

		String s = br.readLine();
		int len = s.length();
		
		// 앞의 한 칸을 비우고 text 배열을 만든다
		for (int i = 0; i < len; i++) {
			text[i + 1] = s.charAt(i);
		}
		
		// dp 배열도 마찬가지로 앞의 한 칸 비우고 사용
		int[] dp = new int[len + 1];
		Arrays.fill(dp, INF);
		dp[0] = 0;
		
		// dp 수행
		for (int right = 1; right <= len; right++) {
			dp[right] = dp[right - 1] + 1;
			for (int left = 1; left <= right; left++) {
				if (isPalindrome(left, right) == TRUE) {
					dp[right] = Math.min(dp[right], dp[left - 1] + 1);
				}
			}
		}

		// 정답 출력
		int answer = dp[len];
		System.out.println(answer);

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < palindromeCache.length; i++) {
			Arrays.fill(palindromeCache[i], CACHE_EMPTY);
		}
	}

	/** text[left]부터 text[right]까지가 팰린드롬인지 여부를 리턴 */
	public static int isPalindrome(int left, int right) {
		if (left >= right) {
			return TRUE;
		}

		if (palindromeCache[left][right] != CACHE_EMPTY) {
			return palindromeCache[left][right];
		}

		if (text[left] == text[right] && isPalindrome(left + 1, right - 1) == TRUE) {
			return palindromeCache[left][right] = TRUE;
		} else {
			return palindromeCache[left][right] = FALSE;
		}
	}
}