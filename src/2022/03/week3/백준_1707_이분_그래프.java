// 274960KB, 916ms

package bj1707;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 20000;
	static final int MAX_E = 20000;
	static final int NO_GROUP = 0, GROUP_1 = 1, GROUP_2 = 2;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] group = new int[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 인접 리스트 초기화
			initMemory();

			// 정점 수, 간선 수 입력
			st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int e = 0; e < E; e++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				adjList[from].add(to);
				adjList[to].add(from);
			}
			
			// 이분 그래프 여부 확인
			boolean isBipartile = isBipartile();
			
			// 정답을 출력 스트링빌더에 추가
			if (isBipartile) {
				sb.append("YES").append("\n");
			}
			else {
				sb.append("NO").append("\n");
			}
			
		} // end for testCase
		
		// 출력
		System.out.print(sb.toString());
		
	} // end main

	/** 이분 그래프 여부를 리턴 */
	public static boolean isBipartile() {
		for (int v = 1; v <= V; v++) {
			if (group[v] == NO_GROUP) {
				group[v] = GROUP_1;
				if (!dfs(v, GROUP_1)) {
					return false;
				}
			}
		}

		return true;
	}

	/** dfs를 수행하면서 이분 그래프가 아니게 되는 경우 false를 리턴 */
	public static boolean dfs(int here, int hereGroup) {
		boolean success = true;
		
		for (int to : adjList[here]) {
			if (group[to] == NO_GROUP) {
				int thereGroup = getOtherGroup(hereGroup);
				group[to] = thereGroup;
				success = success && dfs(to, thereGroup);
			}
			else if (group[to] == hereGroup) {
				return false;
			}
		}
		
		if (success) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/** 그룹 번호가 주어졌을 때, 반대쪽 그룹 번호를 리턴 */
	public static int getOtherGroup(int groupNum) {
		if (groupNum == GROUP_1) {
			return GROUP_2;
		}
		else if (groupNum == GROUP_2) {
			return GROUP_1;
		}
		
		return GROUP_1;
	}
	
	/** 인접 리스트 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(group, NO_GROUP);
	}
}
