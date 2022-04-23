// 25396KB, 240ms

package bj3584;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10000;

	static int N;
	/** */
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];

	/** parent[i]는 i번 노드의 부모 노드 번호 */
	static int[] parent = new int[MAX_N + 1];
	/** depth[i]는 i번 노드의 트리상에서의 깊이 */
	static int[] depth = new int[MAX_N + 1];
	/** isRoot[i]가 true인 노드 i가 루트가 된다 */
	static boolean[] isRoot = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 테스트케이스 수만큼 수행
		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// 정점 수 입력
			N = Integer.parseInt(br.readLine());

			// 간선 정보 입력
			for (int i = 0; i < N - 1; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int p = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				adjList[p].add(c);
				parent[c] = p;
				isRoot[c] = false;
			}

			// 공통 조상을 구할 두 노드 입력
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());

			// 루트 노드 번호 계산
			int root = getRoot();

			// 루트 노드부터 트리를 dfs하면서 depth 계산
			depth[root] = 1;
			dfs(root);

			// 공통 조상 구하기
			int answer = getLCA(node1, node2);

			// 출력 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** dfs를 돌면서 트리 각 노드의 depth 설정 */
	public static void dfs(int now) {
		for (int next : adjList[now]) {
			depth[next] = depth[now] + 1;
			dfs(next);
		}
	}

	/** 전역변수 메모리 초기화 (테스트케이스마다 수행) */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}

		Arrays.fill(parent, 0);
		Arrays.fill(depth, 0);
		Arrays.fill(isRoot, true);
	}

	/** 루트 노드 번호를 구해서 리턴 */
	public static int getRoot() {
		for (int i = 1; i <= N; i++) {
			if (isRoot[i]) {
				return i;
			}
		}
		return -1;
	}

	/** 두 노드의 최소 공통 조상을 구해서 리턴 */
	public static int getLCA(int node1, int node2) {
		if (node1 == node2) {
			return node1;
		}

		int d1 = depth[node1];
		int d2 = depth[node2];

		if (d1 < d2) {
			return getLCA(node1, parent[node2]);
		} else if (d1 > d2) {
			return getLCA(parent[node1], node2);
		} else {
			return getLCA(parent[node1], parent[node2]);
		}
	}

}