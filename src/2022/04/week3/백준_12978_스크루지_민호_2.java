// 54276KB, 460ms

package bj12978;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;

	static int N;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static boolean[] isPoliceStation = new boolean[MAX_N + 1];
	static boolean[] isVisited = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// 간선 정보 입력
		for (int e = 0; e < N - 1; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());
			adjList[node1].add(node2);
			adjList[node2].add(node1);
		}

		// 트리 dp 수행
		dfs(1);

		// 경찰서 수 파악
		int answer = 0;
		for (int nodeNum = 1; nodeNum <= N; nodeNum++) {
			if (isPoliceStation[nodeNum]) {
				answer++;
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** dfs를 수행하고 start 정점의 경찰서 여부를 리턴 */
	public static boolean dfs(int start) {
		isVisited[start] = true;
		boolean ret = false;

		// 경찰서가 없는 자녀가 존재하면, 자신이 경찰서가 된다
		for (int next : adjList[start]) {
			if (!isVisited[next]) {
				if (!dfs(next)) {
					ret = true;
				}
			}
		}

		isPoliceStation[start] = ret;

		return ret;
	}

}