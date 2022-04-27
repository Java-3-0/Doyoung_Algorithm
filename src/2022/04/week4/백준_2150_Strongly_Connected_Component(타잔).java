// 51776KB, 708ms

package bj2150;

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
	static final int MAX_V = 10000, MAX_E = 100000;

	static int V, E;

	static List<Integer>[] adjList;
	static int[] discovered;
	static boolean[] isAssigned;

	static Stack<Integer> stack = new Stack<>();
	static List<List<Integer>> answer = new ArrayList<>();
	static int counter = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		discovered = new int[V + 1];
		isAssigned = new boolean[V + 1];

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
		}

		// 인접 리스트 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}

		// 타잔 알고리즘으로 scc를 구한다
		tarjan();

		// scc들을 문제에서 요구하는대로 정렬
		Collections.sort(answer, (l1, l2) -> l1.get(0) - l2.get(0));

		// SCC의 개수를 sb에 담는다
		sb.append(answer.size()).append("\n");

		// 각 SCC의 정점 번호들을 sb에 담는다
		for (List<Integer> list : answer) {
			for (int num : list) {
				sb.append(num).append(" ");
			}
			sb.append(-1).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** 타잔 알고리즘으로 scc를 구한다 */
	public static void tarjan() {
		Arrays.fill(discovered, -1);
		Arrays.fill(isAssigned, false);

		for (int v = 1; v <= V; v++) {
			if (discovered[v] == -1) {
				dfs(v);
			}
		}

	}

	/** now 이하 서브트리로부터 올라갈 수 있는 정점의 최소 발견 순서를 리턴하면서 scc를 찾는다 */
	public static int dfs(int now) {
		discovered[now] = ++counter;
		int ret = discovered[now];

		stack.push(now);
		for (int next : adjList[now]) {
			// 트리 간선인 경우
			if (discovered[next] == -1) {
				ret = Math.min(ret, dfs(next));
			}
			// 아직 scc로 묶이지 않은 정점으로 가는 교차 간선인 경우
			else if (!isAssigned[next]) {
				ret = Math.min(ret, discovered[next]);
			}
		} // end for next

		if (ret == discovered[now]) {
			List<Integer> scc = new ArrayList<>();

			while (!stack.isEmpty()) {
				int top = stack.peek();
				stack.pop();
				scc.add(top);
				isAssigned[top] = true;
				if (top == now) {
					Collections.sort(scc);
					answer.add(scc);
					break;
				}
			}
		}

		return ret;
	}

}