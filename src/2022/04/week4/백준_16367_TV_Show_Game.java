// 27628KB, 336ms

package bj16367;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_K = 5000, MAX_N = 10000;
	static final int EMPTY = -1;
	static final int TRUE = 1, FALSE = 0;

	static int K, N;
	static Set<Integer>[] adjList = new HashSet[2 * MAX_K + 1];
	static Stack<Integer> stack = new Stack<>();
	static int[] discovered = new int[2 * MAX_K + 1];
	static int[] sccNums = new int[2 * MAX_K + 1];
	static int[] isRed = new int[MAX_K + 1];
	static int vertexCounter = 0;
	static int sccCounter = 0;

	static class Vertex implements Comparable<Vertex> {
		int vNum;
		int sccNum;

		public Vertex(int vNum, int sccNum) {
			super();
			this.vNum = vNum;
			this.sccNum = sccNum;
		}

		/** scc 번호가 내림차순, 즉 위상 정렬 순서가 먼저인 것이 앞에 오도록 정렬 */
		@Override
		public int compareTo(Vertex v) {
			return -(this.sccNum - v.sccNum);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new HashSet<Integer>();
		}

		// 전역변수 메모리 초기화
		initMemory();

		// K, N 입력
		st = new StringTokenizer(br.readLine(), " ");
		K = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		// 각 절의 정보 입력받아서 인접 리스트에 추가
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int[] nodes = new int[3];

			for (int j = 0; j < 3; j++) {
				int lamp = Integer.parseInt(st.nextToken());
				int color = (st.nextToken().charAt(0) == 'R') ? 1 : -1;
				nodes[j] = lamp * color;
			}

			// A, B, C 3개 중 2개가 참이어야 된다는 조건을 (A | B) & (B | C) & (C | A) 형태로 나타낸다.
			for (int j = 0; j < 3; j++) {
				int first = nodes[j];
				int second = nodes[(j + 1) % 3];

				adjList[getNodeNum(-first)].add(getNodeNum(second));
				adjList[getNodeNum(-second)].add(getNodeNum(first));
			}
		}

		// 타잔 알고리즘 수행해서 정점마다 scc 번호를 매긴다
		tarjan();

		// SCC 문제를 풀어서 결과를 출력한다
		if (solve2SAT()) {
			for (int x = 1; x <= K; x++) {
				if (isRed[x] == TRUE) {
					sb.append("R");
				} else {
					sb.append("B");
				}
			}
			sb.append("\n");
		} else {
			sb.append(-1).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		stack.clear();
		Arrays.fill(discovered, EMPTY);
		Arrays.fill(sccNums, EMPTY);
		Arrays.fill(isRed, EMPTY);
		vertexCounter = 0;
		sccCounter = 0;
	}

	/** xi라는 변수는 2 * i로 나타내고, not xi라는 변수는 2 * i + 1로 나타낸다 */
	public static int getNodeNum(int i) {
		int abs = Math.abs(i);

		if (i >= 0) {
			return 2 * abs;
		} else {
			return 2 * abs - 1;
		}
	}

	/** 타잔 알고리즘으로 scc를 구해서, 각 정점마다 scc 번호를 붙인다 */
	public static void tarjan() {
		for (int v = 1; v <= 2 * K; v++) {
			if (discovered[v] == EMPTY) {
				dfs(v);
			}
		}
	}

	/** now 이하 서브트리로부터 올라갈 수 있는 정점의 최소 발견 순서를 리턴하면서 scc를 찾는다 */
	public static int dfs(int now) {
		discovered[now] = ++vertexCounter;
		int ret = discovered[now];

		stack.push(now);

		for (int next : adjList[now]) {
			// 트리 간선인 경우
			if (discovered[next] == EMPTY) {
				ret = Math.min(ret, dfs(next));
			}

			// 아직 scc로 묶이지 않은 정점으로 가는 교차 간선인 경우
			else if (sccNums[next] == EMPTY) {
				ret = Math.min(ret, discovered[next]);
			}

		} // end for next

		// now보다 더 위로 올라갈 수 없는 경우, now까지가 하나의 scc가 된다
		if (ret == discovered[now]) {
			// 지금까지 스택에 담긴 것들의 scc번호를 설정한다
			while (!stack.isEmpty()) {
				int top = stack.peek();
				stack.pop();
				sccNums[top] = sccCounter;
				if (top == now) {
					break;
				}
			}

			sccCounter++;
		}

		return ret;
	}

	/** 현재 adjList라는 그래프 상태에서 2SAT 문제를 푼다 */
	public static boolean solve2SAT() {
		// 타잔 알고리즘을 수행하여 모든 정점에 scc 번호를 붙인다
		tarjan();

		// x와 not x가 같은 scc에 속해 있다면 실패
		for (int i = 1; i <= K; i++) {
			if (sccNums[2 * i - 1] == sccNums[2 * i]) {
				return false;
			}
		}

		// 모든 정점을 pq에 담는다 (scc 번호 내림차순)
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		for (int v = 1; v <= 2 * K; v++) {
			pq.offer(new Vertex(v, sccNums[v]));
		}

		while (!pq.isEmpty()) {
			Vertex vertex = pq.poll();

			// X가 not X보다 먼저 나왔다면 X는 거짓
			if (vertex.vNum % 2 == 0) {
				if (isRed[vertex.vNum / 2] != EMPTY) {
					continue;
				}

				isRed[vertex.vNum / 2] = FALSE;
			}

			// not X가 X보다 먼저 나왔다면 X는 참
			else {
				if (isRed[(vertex.vNum + 1) / 2] != EMPTY) {
					continue;
				}

				isRed[(vertex.vNum + 1) / 2] = TRUE;
			}
		}

		return true;
	}
}