import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class MaxFlowTemplate {
	static final int MAX_V = 10000;
	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static List<Integer>[] adjList;
	static int[][] capacities;
	static int[][] flows;
	static int[] cameFrom;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 전역변수 메모리 할당
		malloc();
		
		// 그래프 입력
		///////////////////////////////
		
		// 최대 유량 계산
		int source = 0;
		int sink = 0;
		int maxFlow = getMaxFlow(source, sink);

		// 출력
		System.out.println(maxFlow);

	} // end main

	/** 전역변수 메모리 할당 */
	public static void malloc() {
		adjList = new ArrayList[MAX_V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		
		capacities = new int[MAX_V + 1][MAX_V + 1];
		flows = new int[MAX_V + 1][MAX_V + 1];
		cameFrom = new int[MAX_V + 1];
	}

	/** source에서 sink로의 최대 유량을 계산해서 리턴 */
	public static int getMaxFlow(int source, int sink) {
		int maxFlow = 0;

		while (true) {
			// 메모리 초기화
			Arrays.fill(cameFrom, NOT_VISITED);

			// BFS 초기 설정
			Queue<Integer> q = new LinkedList<>();
			cameFrom[source] = source;
			q.offer(source);

			// BFS 수행
			while (!q.isEmpty()) {
				// 큐에서 하나를 뽑아온다
				int now = q.poll();

				// 인접해 있는 정점들 탐색
				for (int next : adjList[now]) {
					// 아직 방문하지 않았고, 유량을 보낼 수 있는 정점인 경우
					if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
						// 경로 갱신
						cameFrom[next] = now;
						q.offer(next);
					}

				} // end for (int next : adjList[now])

			} // end while (!q.isEmpty())

			// 도착점까지 가지 못한 경우 무한루프 종료
			if (cameFrom[sink] == NOT_VISITED) {
				break;
			}

			// 도착점까지 간 경우 최대 유량 계산
			int flow = INF;
			int cur = sink;
			while (cur != source) {
				int prev = cameFrom[cur];

				flow = Math.min(flow, capacities[prev][cur] - flows[prev][cur]);

				cur = prev;
			}

			// 최대 유량만큼을 흘려 보낸다
			cur = sink;
			while (cur != source) {
				int prev = cameFrom[cur];

				flows[prev][cur] += flow;
				flows[cur][prev] -= flow;

				cur = prev;
			}

			maxFlow += flow;

		} // end while (true)

		return maxFlow;
	}

}
