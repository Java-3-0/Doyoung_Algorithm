// 143300KB, 516ms

package bj16398;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 1000;
	static final long INF = 987654321987654321L;

	static int V;
	static long[][] adjMatrix = new long[MAX_V][MAX_V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수 입력
		V = Integer.parseInt(br.readLine());
		
		// 인접 행렬 입력
		for (int y = 0; y < V; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < V; x++) {
				adjMatrix[y][x] = Long.parseLong(st.nextToken());
			}
		}
		
		// MST의 길이 계산
		long answer = getLenMST();
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** minimum spanning tree의 길이를 리턴 */
	public static long getLenMST() {
		boolean[] isVisited = new boolean[V];
		long[] dists = new long[V];
		Arrays.fill(dists, INF);

		// 0번 정점에서 시작
		dists[0] = 0;
		
		long ret = 0L;
		for (int i = 0; i < V; i++) {
			// 아직 방문하지 않은 정점들 중 가장 가까운 정점을 찾는다.
			long minDist = INF;
			int minIdx = 0;
			for (int v = 0; v < V; v++) {
				if (!isVisited[v] && dists[v] < minDist) {
					minDist = dists[v];
					minIdx = v;
				}
			}
			
			// 그 정점을 방문하고 mst의 길이에 추가한다.
			isVisited[minIdx] = true;
			ret += minDist;
			
			// 이 정점으로부터 이어진 아직 방문하지 않은 간선들까지의 dists를 갱신
			for (int to = 0; to < V; to++) {
				long dist = adjMatrix[minIdx][to];
				if (!isVisited[to] && dist < dists[to]) {
					dists[to] = dist;
				}
			}
		}
		
		return ret;
	}

}