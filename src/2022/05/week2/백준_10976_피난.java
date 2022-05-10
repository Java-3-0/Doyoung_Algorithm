// 47664KB, 688ms

package bj10976;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;
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
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 간선 수, 정점 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());

				int from = Math.min(x, y);
				int to = Math.max(x, y);

				adjList[from].add(to);
				adjList[to].add(from);

				if (from == 1 || to == V) {
					capacities[from][to] = 1;
				} else {
					capacities[from][to] = INF;
				}
			}

			// 최대 유량 계산
			int answer = getMaxFlow(1, V);

			// 출력 스트링빌더에 담기
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
		
	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}

		for (int i = 0; i < capacities.length; i++) {
			Arrays.fill(capacities[i], 0);
		}

		for (int i = 0; i < flows.length; i++) {
			Arrays.fill(flows[i], 0);
		}

		Arrays.fill(cameFrom, NOT_VISITED);
	}

	/** 최대 유량을 계산해서 리턴 */
	public static int getMaxFlow(int source, int sink) {
		int ret = 0;

		while (true) {
			// 방문 경로 초기화
			Arrays.fill(cameFrom, NOT_VISITED);

			// bfs 초기 설정
			Queue<Integer> q = new ArrayDeque<>();
			cameFrom[source] = source;
			q.offer(source);

			// bfs 수행하면서 source에서 sink로의 capacity가 남은 경로를 찾는다
			while (!q.isEmpty()) {
				int now = q.poll();
				for (int next : adjList[now]) {
					if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
						cameFrom[next] = now;
						q.offer(next);
					}
				}
			}

			// 더 이상 경로가 없으면 루프 종료
			if (cameFrom[sink] == NOT_VISITED) {
				break;
			}

			int cur = sink;
			int flow = INF;
			while (cur != source) {
				int prev = cameFrom[cur];
				flow = Math.min(flow, capacities[prev][cur] - flows[prev][cur]);
				cur = prev;
			}

			cur = sink;
			while (cur != source) {
				int prev = cameFrom[cur];
				flows[prev][cur] += flow;
				flows[cur][prev] -= flow;
				cur = prev;
			}

			ret += flow;
		}

		return ret;
	}

}