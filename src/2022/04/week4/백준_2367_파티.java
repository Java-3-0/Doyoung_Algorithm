// 36972KB, 528ms

package bj2367;

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
	// 0 ~ 199번: 사람, 200 ~ 299번: 요리, 300번: SOURCE, 301번: SINK
	static final int MAX_N = 200, MAX_D = 100;
	static final int MAX_V = MAX_N + MAX_D + 2;
	static final int SOURCE = MAX_N + MAX_D;
	static final int SINK = MAX_N + MAX_D + 1;

	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int N, K, D;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[][] capacities = new int[MAX_V + 1][MAX_V + 1];
	static int[][] flows = new int[MAX_V + 1][MAX_V + 1];

	static int[] cameFrom = new int[MAX_V + 1];
	static boolean[] isInQueue = new boolean[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		// 입력을 받아서 그래프 생성
		getInputAndMakeGraph();

		// 최대 유량 계산
		int maxFlow = getMaxFlow();

		// 출력
		System.out.println(maxFlow);

	} // end main

	/** 입력을 받아서 그래프를 생성 */
	public static void getInputAndMakeGraph() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// N, K, D 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		// SOURCE -> 사람들
		for (int n = 1; n <= N; n++) {
			int person = getPersonIdx(n);

			adjList[SOURCE].add(person);
			adjList[person].add(SOURCE);

			// 사람마다 K개 음식 가능
			capacities[SOURCE][person] += K;
		}

		// 음식들 -> SINK
		st = new StringTokenizer(br.readLine(), " ");
		for (int d = 1; d <= D; d++) {
			int food = getFoodIdx(d);
			int capacity = Integer.parseInt(st.nextToken());

			adjList[food].add(SINK);
			adjList[SINK].add(food);

			capacities[food][SINK] += capacity;
		}

		// 사람들 -> 음식들 입력
		for (int n = 1; n <= N; n++) {
			int person = getPersonIdx(n);
			st = new StringTokenizer(br.readLine(), " ");
			int Z = Integer.parseInt(st.nextToken());

			for (int z = 1; z <= Z; z++) {
				int d = Integer.parseInt(st.nextToken());
				int food = getFoodIdx(d);

				adjList[person].add(food);
				adjList[food].add(person);

				capacities[person][food]++;
			}
		}
	}

	/** source에서 sink로의 최대 유량을 계산해서 리턴 */
	public static int getMaxFlow() {
		int maxFlow = 0;

		while (true) {
			// 메모리 초기화
			Arrays.fill(cameFrom, NOT_VISITED);

			// BFS 초기 설정
			Queue<Integer> q = new LinkedList<>();
			cameFrom[SOURCE] = SOURCE;
			q.offer(SOURCE);

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
			if (cameFrom[SINK] == NOT_VISITED) {
				break;
			}

			// 도착점까지 간 경우 최대 유량 계산
			int flow = INF;
			int cur = SINK;
			while (cur != SOURCE) {
				int prev = cameFrom[cur];

				flow = Math.min(flow, capacities[prev][cur] - flows[prev][cur]);

				cur = prev;
			}

			// 최대 유량만큼을 흘려 보낸다
			cur = SINK;
			while (cur != SOURCE) {
				int prev = cameFrom[cur];

				flows[prev][cur] += flow;
				flows[cur][prev] -= flow;

				cur = prev;
			}

			maxFlow += flow;

		} // end while (true)

		return maxFlow;
	}

	/** 사람 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getPersonIdx(int n) {
		return n - 1;
	}

	/** 음식 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getFoodIdx(int d) {
		return MAX_N + d - 1;
	}
}