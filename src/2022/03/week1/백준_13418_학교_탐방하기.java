package bj13418;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjMatrix 초기화
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}
		for (int y = 0; y < adjMatrix.length; y++) {
			adjMatrix[y][y] = 0;
		}

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 입력
		for (int i = 0; i < E + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int roadType = Integer.parseInt(st.nextToken());

			adjMatrix[from][to] = (roadType == 0) ? 1 : 0;
			adjMatrix[to][from] = (roadType == 0) ? 1 : 0;
		}

		// 최소, 최대 신장 트리 길이 계산
		int minDist = primMin(0);
		int maxDist = primMax(0);

		// 피로도 차이 계산
		int answer = maxDist * maxDist - minDist * minDist;

		// 출력
		System.out.println(answer);

	} // end main

	/** prim 알고리즘으로 최소 신장 트리를 구한다. */
	public static int primMin(int start) {
		boolean[] isVisited = new boolean[V + 1];

		int[] minDist = new int[V + 1];
		Arrays.fill(minDist, INF);

		// start에서 시작
		minDist[start] = 0;
		int result = 0;

		// V + 1개 정점을 방문해야 하니 V + 1번 루프 수행
		for (int i = 0; i <= V; i++) {
			// 가장 비용이 적은 정점을 택한다.
			int min = INF;
			int minVertex = 0;
			for (int v = 0; v <= V; v++) {
				if (!isVisited[v] && minDist[v] < min) {
					min = minDist[v];
					minVertex = v;
				}
			}

			// 정답에 이 정점까지의 간선 길이를 더하고, 방문 여부 갱신
			result += min;
			isVisited[minVertex] = true;

			// 그 정점으로부터의 간선 정보로 minDist를 업데이트
			for (int to = 0; to <= V; to++) {
				if (!isVisited[to] && adjMatrix[minVertex][to] != INF && adjMatrix[minVertex][to] < minDist[to]) {
					minDist[to] = adjMatrix[minVertex][to];
				}
			}
		}

		return result;
	}

	/** adjMatrix에 -부호를 붙여서 prim 알고리즘을 적용함으로써 최대 신장 트리의 길이를 구한다 */
	public static int primMax(int start) {
		boolean[] isVisited = new boolean[V + 1];

		int[] minDist = new int[V + 1];
		Arrays.fill(minDist, 0);

		// start에서 시작
		minDist[start] = 0;
		int result = 0;

		// V + 1개 정점을 방문해야 하니 V + 1번 루프 수행
		for (int i = 0; i < V + 1; i++) {
			// 가장 비용이 적은 정점을 택한다.
			int min = INF;
			int minVertex = 0;
			for (int v = 0; v <= V; v++) {
				if (!isVisited[v] && minDist[v] < min) {
					min = minDist[v];
					minVertex = v;
				}
			}

			// 정답에 이 정점까지의 간선 길이를 더하고, 방문 여부 갱신
			result += min;
			isVisited[minVertex] = true;

			// 그 정점으로부터의 간선 정보로 minDist를 업데이트
			for (int to = 0; to <= V; to++) {
				if (!isVisited[to] && adjMatrix[minVertex][to] != INF && -adjMatrix[minVertex][to] < minDist[to]) {
					minDist[to] = -adjMatrix[minVertex][to];
				}
			}
		}
		
		return -result;
	}
}