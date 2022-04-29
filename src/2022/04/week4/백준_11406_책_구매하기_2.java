// 60160KB, 712ms

package bj11406;

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
	// 0 ~ 99번: 사람, 100 ~ 199번: 서점, 200번: SOURCE, 201번: SINK
	static final int MAX_PEOPLE = 100, MAX_STORES = 100;
	static final int MAX_V = MAX_PEOPLE + MAX_STORES + 2;
	static final int SOURCE = MAX_PEOPLE + MAX_STORES;
	static final int SINK = MAX_PEOPLE + MAX_STORES + 1;

	static final int NOT_VISITED = -1;
	static final int INF = 987654321;

	static int P, S;

	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[][] capacities = new int[MAX_V + 1][MAX_V + 1];
	static int[][] flows = new int[MAX_V + 1][MAX_V + 1];

	static int[] cameFrom = new int[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		// 입력을 받아서 그래프 생성
		getInputAndMakeGraph();

		// 최대 유량 계산
		int maxFlow = getMaxFlow(SOURCE, SINK);

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

		// 사람 수, 서점 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		P = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		// 사람들 -> SINK
		st = new StringTokenizer(br.readLine(), " ");
		for (int pNum = 1; pNum <= P; pNum++) {
			int person = getPersonIdx(pNum);
			int buy = Integer.parseInt(st.nextToken());

			adjList[person].add(SINK);
			adjList[SINK].add(person);

			capacities[person][SINK] += buy;
		}

		// SOURCE -> 서점들
		st = new StringTokenizer(br.readLine(), " ");
		for (int sNum = 1; sNum <= S; sNum++) {
			int store = getStoreIdx(sNum);
			int sell = Integer.parseInt(st.nextToken());

			adjList[SOURCE].add(store);
			adjList[store].add(SOURCE);

			capacities[SOURCE][store] += sell;
		}

		// 서점들 -> 사람들
		for (int sNum = 1; sNum <= S; sNum++) {
			int store = getStoreIdx(sNum);
			st = new StringTokenizer(br.readLine(), " ");

			for (int pNum = 1; pNum <= P; pNum++) {
				int person = getPersonIdx(pNum);
				int capacity = Integer.parseInt(st.nextToken());

				adjList[store].add(person);
				adjList[person].add(store);

				capacities[store][person] = capacity;
			}
		}
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

	/** 서점 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getStoreIdx(int storeNum) {
		return MAX_PEOPLE + storeNum - 1;
	}

	/** 사람 번호가 주어졌을 때, 해당 정점 인덱스를 리턴 */
	public static int getPersonIdx(int personNum) {
		return personNum - 1;
	}
}