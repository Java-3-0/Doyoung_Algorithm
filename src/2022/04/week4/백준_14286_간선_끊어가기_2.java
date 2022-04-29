// 21676KB, 240ms

package bj14286;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500, MAX_E = 10000;
	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[][] capacities = new int[MAX_V + 1][MAX_V + 1];
	static int[][] flows = new int[MAX_V + 1][MAX_V + 1];
	static int[] cameFrom = new int[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList[v1].add(v2);
			adjList[v2].add(v1);

			capacities[v1][v2] += weight;
			capacities[v2][v1] += weight;
		}

		// 시작 정점, 끝 정점 입력
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		// 최소 컷 = 최대 유량이므로 최대 유량을 계산
		int answer = edmondsKarp(start, end);

		// 출력
		System.out.println(answer);

	} // end main

	/** 에드몬드 카프 알고리즘으로 최대 유량을 계산해서 리턴한다 */
	public static int edmondsKarp(int start, int end) {
		int ret = 0;

		// 도착지에 도달하는 경로가 존재하는 동안 무한 반복
		while (true) {
			// 방문한 경로를 기억할 배열을 초기화
			Arrays.fill(cameFrom, NOT_VISITED);

			// bfs를 위한 초기 설정
			Queue<Integer> q = new LinkedList<>();
			q.offer(start);

			BFS: while (!q.isEmpty()) {
				int now = q.poll();
				for (int next : adjList[now]) {
					// 아직 방문하지 않았고 가능한 유량이 남은 정점이면, 어디서 왔는지를 기억하고 큐에 넣는다
					if (cameFrom[next] == NOT_VISITED && capacities[now][next] - flows[now][next] > 0) {
						cameFrom[next] = now;
						q.offer(next);
						if (next == end) {
							break BFS;
						}
					}
				}
			}

			// 도착지에 도달하지 못한 경우 루프 종료
			if (cameFrom[end] == NOT_VISITED) {
				break;
			}

			// 도착지에 도달한 경우 경로에 남은 유량 값들 중 최소 유량 흘려 보내기
			else {
				// 이 경로의 최소 유량 계산
				int minFlow = INF;
				int cur = end;
				while (cur != start) {
					int prev = cameFrom[cur];
					minFlow = Math.min(minFlow, capacities[prev][cur] - flows[prev][cur]);
					cur = prev;
				}

				// 최소 유량만큼 흘려 보낸다
				cur = end;
				while (cur != start) {
					int prev = cameFrom[cur];
					flows[prev][cur] += minFlow;
					flows[cur][prev] -= minFlow;
					cur = prev;
				}

				// 흘려보낸 양만큼 리턴값에 추가
				ret += minFlow;
			}
		}

		return ret;
	}

}