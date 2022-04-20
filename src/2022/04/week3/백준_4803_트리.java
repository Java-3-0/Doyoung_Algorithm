// 60684KB, 408ms

package bj4803;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500, MAX_E = MAX_V * (MAX_V - 1) / 2;

	static int V, E;
	static boolean[] isVisited = new boolean[MAX_V + 1];
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 테스트케이스 수만큼 반복
		int tc = 1;
		while (true) {
			// adjList 초기화
			initAdjList();

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 종료 조건 처리
			if (V == 0 && E == 0) {
				break;
			}

			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				adjList[from].add(to);
				adjList[to].add(from);
			}

			// 트리 수 계산
			int numTrees = getNumberOfTrees();

			// 정답을 스트링빌더에 형식에 맞게 추가
			String answer = getAnswerString(numTrees);
			sb.append("Case ").append(tc).append(": ").append(answer).append("\n");

			tc++;
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 현재 adjList에서 트리 수를 리턴 */
	public static int getNumberOfTrees() {
		// 방문 여부 초기화
		initIsVisited();
		
		int ret = 0;
		for (int v = 1; v <= V; v++) {
			if(!isVisited[v]) {
				if (dfs(v, -1)) {
					ret++;
				}
			}
		}
		
		
		return ret;
	}

	/** 정점 now로부터 dfs를 돌고, tree이면 true, 아니면 false를 리턴한다 */
	public static boolean dfs(int now, int prev) {
		isVisited[now] = true;

		boolean ret = true;
		for (int next : adjList[now]) {
			if (next == prev) {
				continue;
			}
			if (!isVisited[next]) {
				ret &= dfs(next, now);
			}
			else {
				return false;
			}
		}
		
		return ret;
	}

	/** isVisited 초기화 */
	public static void initIsVisited() {
		Arrays.fill(isVisited, false);
	}

	/** adjList 초기화 */
	public static void initAdjList() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
	}

	/** 트리 수가 주어졌을 때, 정답의 형식에 맞는 스트링을 리턴 */
	public static String getAnswerString(int numTrees) {
		if (numTrees == 0) {
			return "No trees.";
		} else if (numTrees == 1) {
			return "There is one tree.";
		} else {
			StringBuilder tmp = new StringBuilder();
			tmp.append("A forest of ").append(numTrees).append(" trees.");
			return tmp.toString();
		}
	}

}