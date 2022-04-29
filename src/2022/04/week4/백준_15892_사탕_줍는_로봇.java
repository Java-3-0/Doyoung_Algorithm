// 18488KB, 232ms

package bj15892;

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
	static final int MAX_V = 300;
	static final int MAX_E = 5000;
	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int V;
	static int E;
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

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			adjList[a].add(b);
			adjList[b].add(a);

			capacities[a][b] += c;
			capacities[b][a] += c;
		}

		// 최대 유량 계산
		int answer = getMaxFlow(1, V);

		// 출력
		System.out.println(answer);

	} // end main

	/** start부터 end까지 가능한 최대 유량을 구해서 리턴한다 */
	private static int getMaxFlow(int start, int end) {
		int ret = 0;
		
		while (true) {
			Arrays.fill(cameFrom, NOT_VISITED);

			Queue<Integer> q = new LinkedList<>();
			cameFrom[start] = start;
			q.offer(start);

			// BFS를 수행하며 플로우가 가능한 경로를 찾는다
			BFS: while (!q.isEmpty()) {
				int now = q.poll();

				for (int next : adjList[now]) {
					if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
						cameFrom[next] = now;
						q.offer(next);
						if (next == end) {
							break BFS;
						}
					}
				}
			}

			// 가능한 경로가 남아있지 않으면 루프 종료
			if (cameFrom[end] == NOT_VISITED) {
				break;
			}

			// 가능한 flow 계산
			int flow = INF;
			int cur = end;
			while (cur != start) {
				int prev = cameFrom[cur];

				flow = Math.min(flow, capacities[prev][cur] - flows[prev][cur]);

				cur = prev;
			}

			// 가능한 flow만큼을 보낸다
			cur = end;
			while (cur != start) {
				int prev = cameFrom[cur];

				flows[prev][cur] += flow;
				flows[cur][prev] -= flow; // 역방향은 음수 플로우 처리

				cur = prev;
			}
			
			ret += flow;
		}

		return ret;
	}

}