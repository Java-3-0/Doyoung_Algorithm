// 35232KB, 336ms

package bj2316;

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
	static final int MAX_V = 400, MAX_E = 10000;
	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[2 * MAX_V + 1];
	static int[][] capacities = new int[2 * MAX_V + 1][2 * MAX_V + 1];
	static int[][] flows = new int[2 * MAX_V + 1][2 * MAX_V + 1];
	static int[] cameFrom = new int[2 * MAX_V + 1];

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

		// 같은 정점 내에서 in -> out 처리
		for (int v = 1; v <= V; v++) {
			int in = getIn(v);
			int out = getOut(v);

			adjList[in].add(out);
			adjList[out].add(in); // 음의 유량을 처리하려면 역방향 간선도 필요하다

			capacities[in][out] = 1;
		}

		// 간선 정보 입력 (모든 간선의 용량을 1로 둔다)
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());

			int in1 = getIn(v1);
			int in2 = getIn(v2);
			int out1 = getOut(v1);
			int out2 = getOut(v2);

			// 다른 정점들 간의 out -> in 처리
			adjList[out1].add(in2);
			adjList[out2].add(in1);
			adjList[in1].add(out2); // 음의 유량을 처리하려면 역방향 간선도 필요하다
			adjList[in2].add(out1); // 음의 유량을 처리하려면 역방향 간선도 필요하다
			
			capacities[out1][in2]++;
			capacities[out2][in1]++;
		}

		// 최대 유량 계산
		int answer = edmondsKarp(getOut(1), getIn(2));

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

			while (!q.isEmpty()) {
				int now = q.poll();
				for (int next : adjList[now]) {
					// 아직 방문하지 않았고 가능한 유량이 남은 정점이면, 어디서 왔는지를 기억하고 큐에 넣는다
					if (cameFrom[next] == NOT_VISITED && capacities[now][next] - flows[now][next] > 0) {
						cameFrom[next] = now;
						q.offer(next);
						if (next == end) {
							break;
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

	/** 정점으로 들어가는 인덱스를 리턴 */
	public static int getIn(int v) {
		return 2 * v;
	}

	/** 정점에서 나오는 인덱스를 리턴 */
	public static int getOut(int v) {
		return 2 * v - 1;
	}

}