// 18464KB, 232ms

package bj1506;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int INF = 987654321;

	static int V;
	static int[] costs;
	static List<Integer>[] adjList;
	static List<Integer>[] revAdjList;
	static boolean[] isVisited;
	static Stack<Integer> stack;
	static List<Integer> tmpSCC;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		costs = new int[V + 1];
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		revAdjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			revAdjList[i] = new ArrayList<Integer>();
		}
		isVisited = new boolean[V + 1];
		stack = new Stack<>();
		tmpSCC = new ArrayList<>();

		// 각 도시 경찰서 비용 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int city = 1; city <= V; city++) {
			costs[city] = Integer.parseInt(st.nextToken());
		}

		// 간선 정보 입력
		for (int from = 1; from <= V; from++) {
			String line = br.readLine();
			for (int to = 1; to <= V; to++) {
				if (line.charAt(to - 1) == '1') {
					adjList[from].add(to);
					revAdjList[to].add(from);
				}
			}
		}

		// 인접 리스트 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}
		for (int i = 0; i < revAdjList.length; i++) {
			Collections.sort(revAdjList[i]);
		}

		// 코사라주 알고리즘으로 scc들을 구한다
		List<List<Integer>> components = kosaraju();

		int answer = 0;

		// 각 SCC에서 가장 비용이 적게 드는 도시를 선택한다
		for (List<Integer> scc : components) {
			int minCost = INF;
			for (int num : scc) {
				minCost = Math.min(minCost, costs[num]);
			}

			answer += minCost;
		}

		System.out.println(answer);

	} // end main

	/** 코사라주 알고리즘으로 scc를 구한다 */
	public static List<List<Integer>> kosaraju() {
		List<List<Integer>> ret = new ArrayList<>();

		// 정방향으로 dfs 하면서 스택에 담는다.
		for (int v = 1; v <= V; v++) {
			if (!isVisited[v]) {
				dfs(v);
			}
		}

		// 방문 여부 초기화
		initIsVisited();

		// 스택에서 하나씩 빼면서 역방향으로 dfs 한다
		while (!stack.isEmpty()) {
			int v = stack.pop();
			if (!isVisited[v]) {
				tmpSCC.clear();
				revDfs(v);
				Collections.sort(tmpSCC);

				List<Integer> scc = new ArrayList<>();
				for (int num : tmpSCC) {
					scc.add(num);
				}

				ret.add(scc);
			}
		}

		// 결과값 정렬
		Collections.sort(ret, (l1, l2) -> l1.get(0) - l2.get(0));
		return ret;
	}

	public static void initIsVisited() {
		Arrays.fill(isVisited, false);
	}

	public static void dfs(int v) {
		isVisited[v] = true;

		for (int next : adjList[v]) {
			if (!isVisited[next]) {
				dfs(next);
			}
		}

		stack.push(v);
	}

	public static void revDfs(int v) {
		isVisited[v] = true;

		for (int next : revAdjList[v]) {
			if (!isVisited[next]) {
				revDfs(next);
			}
		}

		tmpSCC.add(v);
	}

}