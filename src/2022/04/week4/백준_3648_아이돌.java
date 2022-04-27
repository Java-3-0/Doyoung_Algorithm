// 54832KB, 640ms

package bj3648;

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

	static final int MAX_N = 1000, MAX_M = 2000;
	static final int EMPTY = -1;
	static final int TRUE = 1, FALSE = 0;

	static int N, M;
	static Set<Integer>[] adjList = new HashSet[2 * MAX_N + 1];
	static Stack<Integer> stack = new Stack<>();
	static int[] discovered = new int[2 * MAX_N + 1];
	static int[] sccNums = new int[2 * MAX_N + 1];
	static int[] values = new int[MAX_N + 1];
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

		String line = "";
		while ((line = br.readLine()) != null) {
			// 전역변수 메모리 초기화
			initMemory();

			// N, M 입력
			st = new StringTokenizer(line, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			// 각 절의 정보 입력받아서 인접 리스트에 추가
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				adjList[getNodeNum(-a)].add(getNodeNum(b));
				adjList[getNodeNum(-b)].add(getNodeNum(a));
			}
			
			// 상근이는 포함시켜야 한다
			adjList[getNodeNum(-1)].add(getNodeNum(1));

			// 타잔 알고리즘 수행해서 정점마다 scc 번호를 매긴다
			tarjan();

			// SCC 문제를 풀어서 결과를 출력한다
			if (solve2SAT()) {
				sb.append("yes").append("\n");
			} else {
				sb.append("no").append("\n");
			}

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
		Arrays.fill(values, EMPTY);
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
		for (int v = 1; v <= 2 * N; v++) {
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
		for (int i = 1; i <= N; i++) {
			if (sccNums[2 * i - 1] == sccNums[2 * i]) {
				return false;
			}
		}

		// 모든 정점을 pq에 담는다 (scc 번호 내림차순)
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		for (int v = 1; v <= 2 * N; v++) {
			pq.offer(new Vertex(v, sccNums[v]));
		}

		while (!pq.isEmpty()) {
			Vertex vertex = pq.poll();

			// X가 not X보다 먼저 나왔다면 X는 거짓
			if (vertex.vNum % 2 == 0) {
				if (values[vertex.vNum / 2] != EMPTY) {
					continue;
				}

				values[vertex.vNum / 2] = FALSE;
			}

			// not X가 X보다 먼저 나왔다면 X는 참
			else {
				if (values[(vertex.vNum + 1) / 2] != EMPTY) {
					continue;
				}

				values[(vertex.vNum + 1) / 2] = TRUE;
			}
		}

		return true;
	}
}