// 17332KB, 124ms

package bj14517;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_LEN = 1000;
	static final int MOD = 10007;
	static final int CACHE_EMPTY = -1;

	static char[] text;
	static int[][] cache = new int[MAX_LEN][MAX_LEN];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 캐시 초기화
		initCache();
		
		// 입력
		text = br.readLine().toCharArray();

		// 팰린드롬 수 계산
		int len = text.length;
		int answer = countPalindromes(0, len - 1);

		// 출력
		System.out.println(answer);
		
	} // end main
	
	/** 캐시 초기화 */
	public static void initCache() {
		for (int i =0 ;i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** [left, right] 구간에 존재하는 팰린드롬의 개수를 리턴 */
	public static int countPalindromes(int left, int right) {
		// base case
		if (left > right) {
			return 0;
		}
		if (left == right) {
			return 1;
		}
		
		// 캐시에 이미 저장되어 있는 경우
		if (cache[left][right] != CACHE_EMPTY) {
			return cache[left][right];
		}
		
		// 새로 계산하는 경우
		int ret = 0;
		
		// 왼쪽 것을 제외하고 가능한 팰린드롬들 개수
		ret += countPalindromes(left + 1, right);
		ret %= MOD;
		
		// 오른쪽 것을 제외하고 가능한 팰린드롬들 개수
		ret += countPalindromes(left, right - 1);
		ret %= MOD;
		
		// 왼쪽, 오른쪽 것 둘 다 제외하고 가능한 팰린드롬들 개수
		ret -= countPalindromes(left + 1, right - 1);
		ret = (ret + MOD) % MOD;
		
		// 왼쪽, 오른쪽 것 둘 다 포함하고 가능한 팰린드롬들 개수
		if (text[left] == text[right]) {
			ret += (countPalindromes(left + 1, right - 1) + 1);
		}
		ret %= MOD;

		return cache[left][right] = ret;
	}
}