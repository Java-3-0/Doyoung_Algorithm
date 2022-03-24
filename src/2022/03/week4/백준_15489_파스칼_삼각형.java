// 11488KB, 80ms

package bj15489;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_R = 30;
	static final int MAX_C = 30;
	static final int CACHE_EMPTY = -1;

	static int[][] cache = new int[MAX_R + 1][MAX_C + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// R, C, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());

		// 정답 계산
		int answer = getSumOfPascals(R, C, W);

		// 출력
		System.out.println(answer);

	} // end main

	
	public static int getSumOfPascals(int R, int C, int W) {
		int ret = 0;
		for (int dy = 0; dy < W; dy++) {
			int y = R + dy;
			for (int dx = 0; dx <= dy; dx++) {
				int x = C + dx;
				ret += getPascal(y, x);
			}
		}
		
		return ret;
	}

	public static int getPascal(int r, int c) {
		if (c == 1 || c == r) {
			return 1;
		}

		if (cache[r][c] != CACHE_EMPTY) {
			return cache[r][c];
		}
		
		return cache[r][c] = getPascal(r - 1, c - 1) + getPascal(r - 1, c);

	}
	
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}