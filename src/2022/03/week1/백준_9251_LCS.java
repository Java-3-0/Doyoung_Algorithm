// 17076KB, 132ms

package bj9251;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_LEN = 1000;
	static final int CACHE_EMPTY = -1;
	
	static char[] arr1;
	static char[] arr2;
	static int len1;
	static int len2;
	
	static int[][] cache = new int[MAX_LEN][MAX_LEN];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
		
		arr1 = br.readLine().toCharArray();
		arr2 = br.readLine().toCharArray();
		len1 = arr1.length;
		len2 = arr2.length;
		
		int answer = getMaxLengthOfLCS(0, 0);
		
		System.out.println(answer);
	} // end main
	
	/** arr1의 커서가 startIdx1에 있고, arr2의 커서가 startIdx2에 있을 때, 여기서부터 가능한 LCS 길이를 리턴 */
	public static int getMaxLengthOfLCS (int startIdx1, int startIdx2) {
		// base case : 수열의 끝에 도달한 경우
		if (startIdx1 == len1 || startIdx2 == len2) {
			return 0;
		}
		
		// 캐시에 계산된 값이 있는 경우
		if (cache[startIdx1][startIdx2] != CACHE_EMPTY) {
			return cache[startIdx1][startIdx2];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		int ret = 1;
		if (arr1[startIdx1] == arr2[startIdx2]) {
			ret = 1 + getMaxLengthOfLCS(startIdx1 + 1, startIdx2 + 1);
		} else {
			int ret1 = getMaxLengthOfLCS(startIdx1 + 1, startIdx2);
			int ret2 = getMaxLengthOfLCS(startIdx1, startIdx2 + 1);
			
			ret = ret1 < ret2 ? ret2 : ret1;
		}
		
		return cache[startIdx1][startIdx2] = ret;
	}
}