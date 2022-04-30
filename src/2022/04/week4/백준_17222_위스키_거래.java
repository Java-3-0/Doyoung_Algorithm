// 12044KB, 76ms

package bj17222;

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
	// 1 ~ N: 주은이 친구들, N + 1 ~ N + M: 명진이 친구들, 0: SOURCE, MAX_N + MAX_M + 1: SINK
	static final int MAX_N = 100;
	static final int MAX_M = 100;
	static final int MAX_V = MAX_N + MAX_M + 2;
	static final int SOURCE = 0;
	static final int SINK = MAX_N + MAX_M + 1;
	static final int INF = (int) (1e9 + 1e6);
	static final int NOT_VISITED = -1;

	static int N, M;
	static List<Integer>[] adjList = new ArrayList[MAX_V];
	static int[] possibleAmount = new int[MAX_V];
	static int[][] capacities = new int[MAX_V][MAX_V];
	static int[][] flows = new int[MAX_V][MAX_V];
	static int[] cameFrom = new int[MAX_V];

	public static void main(String[] args) throws IOException {
		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 입력 받아서 그래프 생성
		getInputAndMakeGraph();

		// 최대 유량 계산
		int maxFlow = getMaxFlow();

		// 출력
		System.out.println(maxFlow);

	} // end main

	/** 최대 유량을 구해서 리턴 */
	public static int getMaxFlow() {
		int ret = 0;

		while (true) {
			// 방문 기록 초기화
			Arrays.fill(cameFrom, NOT_VISITED);

			// bfs 초기 세팅
			Queue<Integer> q = new LinkedList<>();
			cameFrom[SOURCE] = SOURCE;
			q.offer(SOURCE);

			// bfs 수행
			while (!q.isEmpty()) {
				int now = q.poll();
				for (int next : adjList[now]) {
					if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
						cameFrom[next] = now;
						q.offer(next);
					}
				}
			}

			if (cameFrom[SINK] == NOT_VISITED) {
				break;
			}

			int flow = INF;
			int cur = SINK;
			while (cur != SOURCE) {
				int prev = cameFrom[cur];
				flow = Math.min(flow, capacities[prev][cur] - flows[prev][cur]);
				cur = prev;
			}

			cur = SINK;
			while (cur != SOURCE) {
				int prev = cameFrom[cur];
				flows[prev][cur] += flow;
				cur = prev;
			}

			ret += flow;
		}

		return ret;
	}

	/** 입력을 받아서 그래프 생성 */
	public static void getInputAndMakeGraph() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 주은 친구 수, 명진 친구 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 각 사람이 한 사람으로부터 받을 수 있는 최대 위스키 양 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int n = 1; n <= N; n++) {
			possibleAmount[n] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int m = N + 1; m <= N + M; m++) {
			possibleAmount[m] = Integer.parseInt(st.nextToken());
		}

		possibleAmount[SINK] = INF;
		possibleAmount[SOURCE] = INF;

		// 명진 친구들 -> 다른 사람에게 연락
		for (int from = N + 1; from <= N + M; from++) {
			st = new StringTokenizer(br.readLine(), " ");
			int K = Integer.parseInt(st.nextToken());
			for (int k = 0; k < K; k++) {
				int to = Integer.parseInt(st.nextToken());

				adjList[from].add(to);
				adjList[to].add(from);

				capacities[from][to] += possibleAmount[to];
			}
		}

		// 명진 -> 명진 친구들
		for (int m = N + 1; m <= N + M; m++) {
			adjList[SOURCE].add(m);
			adjList[m].add(SOURCE);

			capacities[SOURCE][m] += possibleAmount[m];
		}

		// 주은 친구들 -> 주은
		for (int n = 1; n <= N; n++) {
			adjList[n].add(SINK);
			adjList[SINK].add(n);

			capacities[n][SINK] += possibleAmount[SINK];
		}
	}
}