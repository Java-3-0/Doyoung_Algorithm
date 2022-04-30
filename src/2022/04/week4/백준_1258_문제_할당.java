// 18960KB, 248ms

package bj1258;

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
	// 0 ~ 99번: 학생, 100 ~ 199번: 문제, 200번: SOURCE, 201번: SINK
	static final int MAX_STUDENTS = 100, MAX_PROBLEMS = 100;
	static final int MAX_V = MAX_STUDENTS + MAX_PROBLEMS + 2;
	static final int SOURCE = MAX_STUDENTS + MAX_PROBLEMS;
	static final int SINK = MAX_STUDENTS + MAX_PROBLEMS + 1;

	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int N;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[][] costs = new int[MAX_V + 1][MAX_V + 1];
	static int[][] capacities = new int[MAX_V + 1][MAX_V + 1];
	static int[][] flows = new int[MAX_V + 1][MAX_V + 1];

	static int[] distances = new int[MAX_V + 1];
	static int[] cameFrom = new int[MAX_V + 1];
	static boolean[] isInQueue = new boolean[MAX_V + 1];

	/** mcmf 알고리즘의 리턴값을 나타낼 결과 객체 */
	static class Result {
		int minCost;
		int maxFlow;

		public Result(int minCost, int maxFlow) {
			super();
			this.minCost = minCost;
			this.maxFlow = maxFlow;
		}

		@Override
		public String toString() {
			return "Result [minCost=" + minCost + ", maxFlow=" + maxFlow + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		// 입력을 받아서 그래프 생성
		getInputAndMakeGraph();

		// 최소 비용 최대 유량 계산
		Result result = mcmf(SOURCE, SINK);

		// 최소 비용 출력
		System.out.println(result.minCost);

	} // end main

	/** 입력을 받아서 그래프를 생성 */
	public static void getInputAndMakeGraph() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 학생 수(문제 수) 입력
		N = Integer.parseInt(br.readLine());

		// SOURCE -> 학생들
		for (int sNum = 1; sNum <= N; sNum++) {
			int student = getStudentIdx(sNum);

			adjList[SOURCE].add(student);
			adjList[student].add(SOURCE);

			capacities[SOURCE][student]++;
		}

		// 문제들 -> SINK
		for (int pNum = 1; pNum <= N; pNum++) {
			int problem = getProblemIdx(pNum);

			adjList[problem].add(SINK);
			adjList[SINK].add(problem);

			capacities[problem][SINK]++;
		}

		// 학생들 -> 문제들
		for (int sNum = 1; sNum <= N; sNum++) {
			int student = getStudentIdx(sNum);
			st = new StringTokenizer(br.readLine(), " ");

			for (int pNum = 1; pNum <= N; pNum++) {
				int problem = getProblemIdx(pNum);
				int cost = Integer.parseInt(st.nextToken());

				adjList[student].add(problem);
				adjList[problem].add(student);

				capacities[student][problem] = 1;

				costs[student][problem] += cost;
				costs[problem][student] -= cost;
			}
		}
	}

	/** source에서 sink로의 최소 비용 최대 유량을 계산해서 리턴 */
	public static Result mcmf(int source, int sink) {
		int minCost = 0;
		int maxFlow = 0;

		while (true) {
			// 메모리 초기화
			Arrays.fill(distances, INF);
			Arrays.fill(cameFrom, NOT_VISITED);
			Arrays.fill(isInQueue, false);

			// BFS 초기 설정
			Queue<Integer> q = new LinkedList<>();
			distances[source] = 0;
			cameFrom[source] = source;
			isInQueue[source] = true;
			q.offer(source);

			// BFS 수행
			while (!q.isEmpty()) {
				// 큐에서 하나를 뽑아온다
				int now = q.poll();
				isInQueue[now] = false;

				// 인접해 있는 정점들 탐색
				for (int next : adjList[now]) {
					// 유량을 보낼 수 있고, 최소 비용을 갱신할 수 있는 정점인 경우
					if (flows[now][next] < capacities[now][next]
							&& distances[now] + costs[now][next] < distances[next]) {
						// 비용과 경로 갱신
						distances[next] = distances[now] + costs[now][next];
						cameFrom[next] = now;

						// 큐에 없는 정점이면 넣는다
						if (!isInQueue[next]) {
							isInQueue[next] = true;
							q.offer(next);
						}
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

				minCost += flow * costs[prev][cur];

				cur = prev;
			}

			maxFlow += flow;

		} // end while (true)

		return new Result(minCost, maxFlow);
	}

	/** 문제 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getProblemIdx(int pNum) {
		return MAX_STUDENTS + pNum - 1;
	}

	/** 학생 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getStudentIdx(int sNum) {
		return sNum - 1;
	}
}