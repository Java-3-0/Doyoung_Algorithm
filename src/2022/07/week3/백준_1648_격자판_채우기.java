// 25488KB, 120ms

package boj1648;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 14, MAX_W = 14;
	static final int MAX_BITMASK = (1 << MAX_W) - 1;
	static final int CACHE_EMPTY = -1;
	static final int MOD = 9901;

	static int H, W;
	static int[][] cache = new int[MAX_H * MAX_W + 1][MAX_BITMASK + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 격자판 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 캐시 초기화
		initCache();

		// 정답 계산
		int answer = countWaysToFill(0, 0);

		System.out.println(answer);

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** idx는 현재 채울 칸의 위치, used는 현재 칸부터 W개의 칸의 사용 정보로 주어졌을 때 채우는 방법의 수를 리턴 */
	public static int countWaysToFill(int idx, int used) {
		// 1. base case
		if (idx == H * W) {
			return 1;
		}

		// 2. memoization
		if (cache[idx][used] != CACHE_EMPTY) {
			return cache[idx][used];
		}

		// 3-1. 이미 채운 칸인 경우
		if ((used & 1) != 0) {
			return cache[idx][used] = countWaysToFill(idx + 1, (used >> 1));
		}

		// 3-2. 새로 채우는 경우
		int ret = 0;

		// 좌표 계산
		int y = idx / W;
		int x = idx % W;

		// 아래랑 묶는 경우
		if (y + 1 < H) {
			int cntBottom = countWaysToFill(idx + 1, (used >> 1) | (1 << (W - 1)));
			if (cntBottom > 0) {
				ret += cntBottom;
				ret %= MOD;
			}
		}

		// 오른쪽과 묶는 경우
		if (x + 1 < W && (used & (1 << 1)) == 0) {
			int cntRight = countWaysToFill(idx + 1, (used >> 1) | 1);
			if (cntRight > 0) {
				ret += cntRight;
				ret %= MOD;
			}
		}

		return cache[idx][used] = ret;
	}
}