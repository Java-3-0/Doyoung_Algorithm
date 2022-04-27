// 179380KB, 1156ms

package bj3977;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000, MAX_E = 100000;
	static final int EMPTY = -1;

	static int V, E;

	static List<Integer>[] adjList = new ArrayList[MAX_V];
	static int[] discovered = new int[MAX_V];
	static int[] sccNums = new int[MAX_V];
	static int[] indegreesOfSCC = new int[MAX_V];
	static Stack<Integer> stack = new Stack<>();
	static int vertexCounter = 0;
	static int sccCounter = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 공간 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				adjList[from].add(to);
			}

			// 타잔 알고리즘 수행
			tarjan();

			// SCC들의 indegree를 계산한다
			calcIndegrees();

			// SCC들 중 indegree가 0인 것들을 구한다
			List<Integer> indegreeZeroSCCs = getIndegreeZeroSCCs();

			// 1개면 그 scc의 정점들이 시작 지점으로 가능
			if (indegreeZeroSCCs.size() == 1) {
				int targetSCC = indegreeZeroSCCs.get(0);
				for (int v = 0; v < V; v++) {
					if (sccNums[v] == targetSCC) {
						sb.append(v).append("\n");
					}
				}
			}

			// 2개 이상이면 불가능
			else {
				sb.append("Confused").append("\n");
			}

			// 빈 줄로 입력 및 출력 구분
			if (tc != TESTS) {
				br.readLine();
				sb.append("\n");
			}
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}

		Arrays.fill(discovered, EMPTY);
		Arrays.fill(sccNums, EMPTY);
		Arrays.fill(indegreesOfSCC, 0);
		stack.clear();
		vertexCounter = 0;
		sccCounter = 0;
	}

	/** 타잔 알고리즘으로 scc를 구한다 */
	public static void tarjan() {
		for (int v = 0; v < V; v++) {
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

	/** 모든 scc들의 indegree 값을 계산해서 indegreesOfSCC에 저장한다 */
	public static void calcIndegrees() {
		// 모든 간선을 탐색
		for (int from = 0; from < V; from++) {
			for (int to : adjList[from]) {
				// 다른 scc로의 간선이 존재하는 경우, indegree를 센다
				if (sccNums[from] != sccNums[to]) {
					indegreesOfSCC[sccNums[to]]++;
				}
			}
		}
	}

	/** indegree가 0인 scc들을 찾아서 리턴 */
	public static List<Integer> getIndegreeZeroSCCs() {
		List<Integer> ret = new ArrayList<>();

		for (int scc = 0; scc < sccCounter; scc++) {
			if (indegreesOfSCC[scc] == 0) {
				ret.add(scc);
			}
		}

		return ret;
	}

}