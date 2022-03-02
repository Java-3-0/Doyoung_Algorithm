package bj17404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int COLORS = 3;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	
	static int N;
	static int[][] costs = new int[MAX_N][COLORS];
	static int[][][] cache = new int[MAX_N][COLORS][COLORS];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 캐시 초기화
		initCache();
		
		// 집의 수 입력
		N = Integer.parseInt(br.readLine());
		
		// 각 집을 각 색깔로 칠하는 비용 입력
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int color = 0; color < 3; color++) {
				costs[y][color] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 첫 집을 칠하는 3가지 방법에 대해서 각각 두번째 집부터 getMinCosts 함수를 호출해서 계산
		int answer = Integer.MAX_VALUE;
		for (int color = 0; color < COLORS; color++) {
			answer = Math.min(answer, costs[0][color] + getMinCosts(1, color, color));
		}
		
		// 정답 출력
		System.out.println(answer);

	} // end main

	/** 캐시를 초기화하는 함수 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[0].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}
	
	/** 첫 집의 색이 firstColor이고, (beginIdx - 1)번 집의 색이 prevColor일 때, beginIdx번 집부터 가능한 최소 비용을 리턴 */
	public static int getMinCosts(int beginIdx, int prevColor, int firstColor) {
		// base case : 끝까지 칠한 경우
		if (beginIdx == N) {
			// 첫 집 색 == 마지막 집 색이면 잘못된 칠하기 방법이므로 INF값을 리턴
			if (prevColor == firstColor) {
				return INF;
			}
			// 이외의 경우 칠하기 성공
			else {
				return 0;
			}
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[beginIdx][prevColor][firstColor] != CACHE_EMPTY) {
			return cache[beginIdx][prevColor][firstColor] ;
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		int ret = Integer.MAX_VALUE;
		for (int i = 1; i <= 2; i++) {
			int colorOfThis = (prevColor + i) % COLORS;
			int costOfThis = costs[beginIdx][colorOfThis];
			ret = Math.min(ret, costOfThis + getMinCosts(beginIdx + 1, colorOfThis, firstColor));
		}
		
		return cache[beginIdx][prevColor][firstColor]  = ret;
	}
}