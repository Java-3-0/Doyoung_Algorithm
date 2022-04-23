// 88428KB, 688ms

package bj14267;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;

	static int N, M;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] compliments = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수, 최초 칭찬 횟수
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 부모 정보 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int child = 1; child <= N; child++) {
			int parent = Integer.parseInt(st.nextToken());
			if (parent != -1) {
				adjList[parent].add(child);
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int nodeNum = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			compliments[nodeNum] += weight;
		}
		
		// 트리 dp 수행해서 각 노드의 칭찬 계산
		dfs(1);

		// 출력
		for (int i = 1; i <= N; i++) {
			sb.append(compliments[i]).append(" ");
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

	/** dfs를 수행 */
	public static void dfs(int start) {
		// 칭찬을 부하직원에게 전달
		for (int next : adjList[start]) {
			compliments[next] += compliments[start];
			dfs(next);
		}
	}

}