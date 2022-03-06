// 16332KB, 148ms

package bj2098;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 16;
	static final int CACHE_EMPTY = -1;
	static final int BITMASK = (1 << MAX_V);

	static final int INF = 987654321;

	static int V;
	static int[][] adjMatrix = new int[MAX_V][MAX_V];
	static int[][] cache = new int[MAX_V][BITMASK];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 도시 수 입력
		V = Integer.parseInt(br.readLine());

		// 인접 행렬 입력
		for (int y = 0; y < V; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < V; x++) {
				int input = Integer.parseInt(st.nextToken());
				if (y != x && input == 0) {
					adjMatrix[y][x] = INF;
				} else {
					adjMatrix[y][x] = input;
				}
			}
		}

		// 외판원 순회 최소 비용을 구한다.
		int answer = minCost(0, (1 << 0));

		// 출력
		System.out.println(answer);

	} // end main

	/** 현재 위치와 현재까지 방문한 정점 비트마스킹 정보가 주어졌을 때, 남은 경로의 최소 코스트를 리턴 */
	public static int minCost(int nowIdx, int visited) {
		// base case : 모두 방문한 경우
		if (visited == (1 << V) - 1) {
			// 시작점까지 돌아가는 거리를 리턴
			return adjMatrix[nowIdx][0];
		}

		// 캐시에 이미 계산되어 있는 경우
		if (cache[nowIdx][visited] != CACHE_EMPTY) {
			return cache[nowIdx][visited];
		}

		// 새로 계산해서 캐시에 저장하는 경우
		// 아직 방문하지 않은 정점들로 가 보면서 ret 값 갱신
		int ret = INF;
		for (int to = 0; to < V; to++) {
			if (adjMatrix[nowIdx][to] != INF && (visited & (1 << to)) == 0) {
				ret = Math.min(ret, adjMatrix[nowIdx][to] + minCost(to, (visited | (1 << to))));
			}
		}

		return cache[nowIdx][visited] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}