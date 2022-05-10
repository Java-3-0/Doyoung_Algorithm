// 23904KB, 212ms

package bj1420;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100, MAX_W = 100;
	static final int MAX_V = 2 * MAX_H * MAX_W;
	static final int NOT_VISITED = -1;
	static final int[] dy = { 0, 0, -1, 1 };
	static final int[] dx = { -1, 1, 0, 0 };
	static final char HOME = 'K', SCHOOL = 'H', WALL = '#', EMPTY = '.';
	static final int INF = 987654321;

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
	static List<Integer>[] adjList = new ArrayList[MAX_V];
	static Map<Integer, Integer>[] capacities = new HashMap[MAX_V];
	static Map<Integer, Integer>[] flows = new HashMap[MAX_V];
	static int[] cameFrom = new int[MAX_V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 전역변수 메모리 할당
		malloc();

		// cameFrom 초기화
		Arrays.fill(cameFrom, NOT_VISITED);

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 그리드 입력
		int source = 0, sink = 0;
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				char c = line.charAt(x);
				grid[y][x] = c;
				if (c == 'K') {
					source = getOutIdx(getPointNumber(y, x));
				} else if (c == 'H') {
					sink = getInIdx(getPointNumber(y, x));
				}
			}
		}

		// 그래프 생성
		makeGraph();

		// 최대 유량 계산
		int maxFlow = getMaxFlow(source, sink);

		// source와 sink가 바로 이어져 있는 경우 불가능
		if (adjList[source].contains(sink)) {
			System.out.println(-1);
		}
		// 그 외의 경우 maxFlow = minCut
		else {
			System.out.println(maxFlow);
		}

	} // end main

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
					if (cameFrom[next] == NOT_VISITED && flows[now].get(next) < capacities[now].get(next)) {
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
				int remainingCapacity = capacities[prev].get(cur) - flows[prev].get(cur);
				flow = Math.min(flow, remainingCapacity);
				cur = prev;
			}

			cur = sink;
			while (cur != source) {
				int prev = cameFrom[cur];
				flows[prev].put(cur, flows[prev].get(cur) + flow);
				flows[cur].put(prev, flows[cur].get(prev) - flow);
				cur = prev;
			}

			ret += flow;
		}

		return ret;
	}

	/** 전역변수 메모리 할당 */
	public static void malloc() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < capacities.length; i++) {
			capacities[i] = new HashMap<Integer, Integer>();
		}
		for (int i = 0; i < flows.length; i++) {
			flows[i] = new HashMap<Integer, Integer>();
		}
	}

	/** grid 정보대로 그래프 생성 (adjList, capacities, flows를 세팅) */
	public static void makeGraph() {
		// 자기 자신 내에서의 (in -> out) 간선 정보 생성
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (grid[y][x] == WALL) {
					continue;
				}

				int point = getPointNumber(y, x);
				int in = getInIdx(point);
				int out = getOutIdx(point);

				adjList[in].add(out);
				adjList[out].add(in);

				capacities[in].put(out, 1);
				capacities[out].put(in, 0);

				flows[in].put(out, 0);
				flows[out].put(in, 0);
			}
		}

		// 다른 정점으로의 (out -> in) 간선 정보 생성
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (grid[y][x] == WALL) {
					continue;
				}

				int fromPoint = getPointNumber(y, x);
				int fromOut = getOutIdx(fromPoint);

				for (int dir = 0; dir < dy.length; dir++) {
					int nextY = y + dy[dir];
					int nextX = x + dx[dir];
					if (isInRange(nextY, nextX) && grid[nextY][nextX] != WALL) {
						int toPoint = getPointNumber(nextY, nextX);
						int toIn = getInIdx(toPoint);

						adjList[fromOut].add(toIn);
						adjList[toIn].add(fromOut);

						capacities[fromOut].put(toIn, 1);
						capacities[toIn].put(fromOut, 0);

						flows[fromOut].put(toIn, 0);
						flows[toIn].put(fromOut, 0);
					}
				}
			}
		}
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}

	/** (y, x)의 정점 번호를 리턴 */
	public static int getPointNumber(int y, int x) {
		return W * y + x;
	}

	/** pointNum번 정점을 둘로 분할했을 때 들어오는 정점 번호를 리턴 */
	public static int getInIdx(int pointNum) {
		return 2 * pointNum;
	}

	/** pointNum번 정점을 둘로 분할했을 때 나가는 정점 번호를 리턴 */
	public static int getOutIdx(int pointNum) {
		return 2 * pointNum + 1;
	}
}