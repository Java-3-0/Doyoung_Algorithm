// 68124KB, 248ms

package bj9252;

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
	static Index[][] nextIndex = new Index[MAX_LEN][MAX_LEN];

	static class Index {
		int idx1;
		int idx2;

		public Index(int idx1, int idx2) {
			super();
			this.idx1 = idx1;
			this.idx2 = idx2;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
		for (int i = 0; i < nextIndex.length; i++) {
			for (int x = 0; x < nextIndex[i].length; x++) {
				nextIndex[i][x] = new Index(-1, -1);
			}
		}

		arr1 = br.readLine().toCharArray();
		arr2 = br.readLine().toCharArray();
		len1 = arr1.length;
		len2 = arr2.length;

		int answer = getMaxLengthOfLCS(0, 0);

		System.out.println(answer);
		if (answer != 0) {
			Index idx = new Index(0, 0);

			while (true) {
				if (idx.idx1 < 0 || idx.idx1 >= len1 || idx.idx2 < 0 || idx.idx2 >= len2) {
					break;
				}

				if (arr1[idx.idx1] == arr2[idx.idx2]) {
					sb.append(arr1[idx.idx1]);
				}

				idx = nextIndex[idx.idx1][idx.idx2];
			}
			sb.append("\n");
			
			System.out.print(sb.toString());
		}

	} // end main

	/** arr1의 커서가 startIdx1에 있고, arr2의 커서가 startIdx2에 있을 때, 여기서부터 가능한 LCS 길이를 리턴 */
	public static int getMaxLengthOfLCS(int startIdx1, int startIdx2) {
		// base case : 수열의 끝에 도달한 경우
		if (startIdx1 == len1 || startIdx2 == len2) {
			return 0;
		}

		// 캐시에 계산된 값이 있는 경우
		if (cache[startIdx1][startIdx2] != CACHE_EMPTY) {
			return cache[startIdx1][startIdx2];
		}

		int ret = 1;
		if (arr1[startIdx1] == arr2[startIdx2]) {
			ret = 1 + getMaxLengthOfLCS(startIdx1 + 1, startIdx2 + 1);
			nextIndex[startIdx1][startIdx2] = new Index(startIdx1 + 1, startIdx2 + 1);
		} else {
			int ret1 = getMaxLengthOfLCS(startIdx1 + 1, startIdx2);
			int ret2 = getMaxLengthOfLCS(startIdx1, startIdx2 + 1);

			if (ret1 < ret2) {
				ret = ret2;
				nextIndex[startIdx1][startIdx2] = new Index(startIdx1, startIdx2 + 1);
			} else {
				ret = ret1;
				nextIndex[startIdx1][startIdx2] = new Index(startIdx1 + 1, startIdx2);
			}
		}

		// 새로 계산해서 캐시에 넣는 경우
		return cache[startIdx1][startIdx2] = ret;
	}
}