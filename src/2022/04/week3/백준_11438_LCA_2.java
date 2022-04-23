// 103320KB, 896ms

package bj11438;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;
	
	static int N, M;
	static int LOG_N;
	static List<Integer>[] adjList;

	/** parent[i]는 i번 노드의 부모 노드 번호 */
	static int[][] parentST;
	/** depth[i]는 i번 노드의 트리상에서의 깊이 */
	static int[] depth;
	/** dfs를 위한 방문 여부 체크 배열 */
	static boolean[] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// 정점 수의 log 계산
		LOG_N = (int) (Math.ceil(Math.log(N) / Math.log(2)));

		// 전역변수 메모리 할당
		adjList = new ArrayList[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		parentST = new int[LOG_N + 1][N + 1];
		depth = new int[N + 1];
		isVisited = new boolean[N + 1];

		// 간선 정보 입력
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			adjList[n1].add(n2);
			adjList[n2].add(n1);
		}

		// 루트 노드부터 트리를 dfs하면서 depth 계산
		depth[1] = 1;
		isVisited[1] = true;
		dfs(1);
		
		// sparseTable 생성
		makeParentSparseTable();

		// 쿼리 수 입력
		M = Integer.parseInt(br.readLine());
		for (int i = 0; i < M; i++) {
			// 공통 조상을 구할 두 노드 입력
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());

			// 공통 조상 구하기
			int answer = getLCA(node1, node2);

			// 출력 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** dfs를 돌면서 트리 각 노드의 depth 설정, parentST의 첫 줄도 설정 */
	public static void dfs(int now) {
		for (int next : adjList[now]) {
			if (!isVisited[next]) {
				isVisited[next] = true;
				parentST[0][next] = now;
				depth[next] = depth[now] + 1;
				dfs(next);
			}

		}
	}

	/** sparse table을 만든다 */
	public static void makeParentSparseTable() {
		int len = parentST.length;
		for (int k = 1; k < len; k++) {
			for (int num = 1; num <= N; num++) {
				parentST[k][num] = parentST[k - 1][parentST[k - 1][num]];
			}
		}
	}

	/** 두 노드의 최소 공통 조상을 구해서 리턴 */
	public static int getLCA(int node1, int node2) {
		int d1 = depth[node1];
		int d2 = depth[node2];

		// d1 > d2이면 d1 <= d2인 함수로 뒤집어서 호출
		if (d1 > d2) {
			return getLCA(node2, node1);
		}

		int diff = d2 - d1;
		// node2의 diff번째 조상(node1과 같은 depth인 것)을 구한다
		for (int k = LOG_N; k >= 0; k--) {
			if ((diff & (1 << k)) != 0) {
				node2 = parentST[k][node2];
			}
		}

		// 여기서 node1과 node2가 같아지면 여기가 루트이다
		if (node1 == node2) {
			return node1;
		}

		// 2의 k승으로 점프하면서 조상이 다를 때마다 a와 b를 위로 올린다
		for (int k = LOG_N; k >= 0; k--) {
			if (parentST[k][node1] != parentST[k][node2]) {
				node1 = parentST[k][node1];
				node2 = parentST[k][node2];
			}
		}

		// 마지막 상태에서 a와 b의 부모가 최소 공통 조상
		return parentST[0][node1];
	}

}