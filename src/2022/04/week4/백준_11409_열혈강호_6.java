// 82976KB, 1880ms

package bj11409;

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
	static final int MAX_N = 400, MAX_M = 400;
	// 정점 번호는 0 ~ 399: 직원, 400 ~ 799: 일, 800: 시작점, 801: 도착점
	static final int START = 800, END = 801;
	static final int MAX_V = MAX_N + MAX_M + 2;
	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int N, M;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];

	static int[][] capacities = new int[MAX_V + 1][MAX_V + 1];
	static int[][] flows = new int[MAX_V + 1][MAX_V + 1];
	static int[][] costs = new int[MAX_V + 1][MAX_V + 1];

	static int[] cameFrom = new int[MAX_V + 1];
	static int[] distances = new int[MAX_V + 1];
	static boolean[] isInQueue = new boolean[MAX_V + 1];

	/** mcmf 알고리즘의 결과값을 저장할 객체 */
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

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 입력을 받아서 그래프 생성
		getInputandMakeGraph();

		// 최소 비용 최대 유량 계산
		Result result = mcmf(START, END);

		// 출력
		System.out.println(result.maxFlow);
		System.out.println(-result.minCost);

	} // end main

	/** 입력을 받아서 그래프 생성 */
	public static void getInputandMakeGraph() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 직원 수, 일 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 시작점 -> 직원으로의 간선 생성.
		for (int empNum = 1; empNum <= N; empNum++) {
			int employee = getEmployeeIdx(empNum);

			adjList[START].add(employee);
			adjList[employee].add(START);

			capacities[START][employee]++;
		}

		// 일 -> 도착점으로의 간선 생성
		for (int jobNum = 1; jobNum <= M; jobNum++) {
			int job = getJobIdx(jobNum);

			adjList[job].add(END);
			adjList[END].add(job);

			capacities[job][END]++;
		}

		// 직원 -> 일로의 간선 입력받아서 생성
		for (int empNum = 1; empNum <= N; empNum++) {
			int employee = getEmployeeIdx(empNum);

			st = new StringTokenizer(br.readLine(), " ");
			int cnt = Integer.parseInt(st.nextToken());
			for (int k = 0; k < cnt; k++) {
				int jobNum = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());

				int job = getJobIdx(jobNum);

				adjList[employee].add(job);
				adjList[job].add(employee);

				capacities[employee][job]++;

				// 최대 비용을 구하는 대신, 부호를 반대로 처리한 최소 비용을 구할 것이다
				costs[employee][job] = -cost;
				costs[job][employee] = +cost; 
			}
		}
	}

	/** start부터 end까지 가능한 최대 유량을 구해서 리턴한다 */
	private static Result mcmf(int start, int end) {
		int minCost = 0;
		int maxFlow = 0;

		while (true) {
			// 초기화
			Arrays.fill(cameFrom, NOT_VISITED);
			Arrays.fill(distances, INF);
			Arrays.fill(isInQueue, false);

			// BFS를 위한 초기 설정
			Queue<Integer> q = new LinkedList<>();
			cameFrom[start] = start;
			distances[start] = 0;
			isInQueue[start] = true;
			q.offer(start);

			// BFS를 수행하며 플로우가 가능한 경로 중 최단 경로를 찾는다 (SPFA 알고리즘)
			while (!q.isEmpty()) {
				int now = q.poll();
				isInQueue[now] = false;

				for (int next : adjList[now]) {
					// 유량을 보내는 것이 가능하고, 최단 경로 길이가 더 짧아지는 경우
					if (flows[now][next] < capacities[now][next]
							&& distances[now] + costs[now][next] < distances[next]) {
						distances[next] = distances[now] + costs[now][next];
						cameFrom[next] = now;

						// 큐에 없으면 넣는다
						if (!isInQueue[next]) {
							isInQueue[next] = true;
							q.offer(next);
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

				// 비용 갱신
				minCost += costs[prev][cur] * flow;

				cur = prev;
			}

			maxFlow += flow;

		} // end while(true)

		return new Result(minCost, maxFlow);
	}

	/** 직원 번호가 주어졌을 때 해당하는 정점 번호를 리턴 */
	public static int getEmployeeIdx(int empNum) {
		return (empNum - 1);
	}

	/** 일 번호가 주어졌을 때 해당하는 정점 번호를 리턴 */
	public static int getJobIdx(int jobNum) {
		return MAX_N + (jobNum - 1);
	}

}