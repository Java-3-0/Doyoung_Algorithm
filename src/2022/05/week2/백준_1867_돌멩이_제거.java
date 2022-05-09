// 15284KB, 144ms

package bj1867;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500, MAX_E = 10000;
	static final int NOT_MATCHED = -1;

	static int V, E;

	/** 정의역 -> 공역으로의 간선 정보 */
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	/** 공역의 각 원소를 다른 정의역과 매칭하려는 시도를 해 봤는지 여부를 나타내는 배열 */
	static boolean[] isChecked = new boolean[MAX_V + 1];
	/** 공역의 각 원소가 정의역에 어떤 원소와 매칭되었는지를 나타내는 배열 */
	static int[] isMatchedTo = new int[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 전역변수 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 전역변수 메모리 초기화
		initMemory();

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

		// 간선 정보 오름차순정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}

		// 최소 정점 커버 = 최대 매칭
		int answer = getMaxMatches();

		// 출력
		System.out.println(answer);

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isChecked, false);
		Arrays.fill(isMatchedTo, NOT_MATCHED);
	}

	/** 현재 adjList에서 가능한 최대 매칭 수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		for (int x = 1; x <= V; x++) {
			Arrays.fill(isChecked, false);

			if (dfs(x)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 매칭 시도 */
	public static boolean dfs(int x) {
		for (int y : adjList[x]) {
			if (!isChecked[y]) {
				isChecked[y] = true;
				if (isMatchedTo[y] == NOT_MATCHED || dfs(isMatchedTo[y])) {
					isMatchedTo[y] = x;
					return true;
				}
			}
		}

		return false;
	}

}