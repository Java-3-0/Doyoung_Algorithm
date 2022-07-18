// 12308KB, 140ms

package boj1102;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 16;
	static final int MAX_P = MAX_N;
	static final int MAX_BITMASK = (1 << MAX_N) - 1;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, P;
	static int[][] costs = new int[MAX_N][MAX_N];
	static int[] cache = new int[MAX_BITMASK + 1];
	static int targetCnt;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 공장 개수 입력
		N = Integer.parseInt(br.readLine());

		// 비용 정보 입력
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				costs[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int bitmask = 0;
		String factoryStr = br.readLine();
		for (int factoryIdx = 0; factoryIdx < factoryStr.length(); factoryIdx++) {
			boolean isOn = factoryStr.charAt(factoryIdx) == 'Y' ? true : false;

			if (isOn) {
				bitmask |= (1 << factoryIdx);
			}
		}

		// 꺼져 있어도 되는 공장 개수 입력
		P = Integer.parseInt(br.readLine());

		// 캐시 초기화
		initCache();

		// 정답 계산
		int answer = getMinCost(bitmask);

		if (answer == INF) {
			System.out.println(-1);
		} else {
			System.out.println(answer);
		}

	} // end main

	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	public static int getMinCost(int bitmask) {
		if (Integer.bitCount(bitmask) >= P) {
			return 0;
		}

		if (cache[bitmask] != CACHE_EMPTY) {
			return cache[bitmask];
		}

		int ret = INF;

		for (int from = 0; from < N; from++) {
			if ((bitmask & (1 << from)) != 0) {
				for (int to = 0; to < N; to++) {
					if ((bitmask & (1 << to)) == 0) {
						int nextBitmask = (bitmask | (1 << to));
						ret = Math.min(ret, costs[from][to] + getMinCost(nextBitmask));
					}
				}
			}
		}

		return cache[bitmask] = ret;
	}

}