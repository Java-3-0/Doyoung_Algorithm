// 72832KB, 668ms

package boj1693;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int MAX_COLORS = (int) Math.ceil(Math.log(MAX_N));
	static final long INF = 9876543210987654L;
	static final long CACHE_EMPTY = 0L; // 칠했다면 0이 될 수 없으므로, 0으로 초기화해 놓고 칠하면서 비용을 합산

	static int N;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static long[][] cache = new long[MAX_N + 1][MAX_COLORS + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 정점 개수 입력
		N = Integer.parseInt(br.readLine());

		// 트리 간선 정보 입력
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());

			adjList[v1].add(v2);
			adjList[v2].add(v1);
		}

		// 루트를 칠하는 모든 방법 중 최선의 방법 찾기
		long answer = INF;
		for (int rootColor = 1; rootColor <= MAX_COLORS; rootColor++) {
			answer = Math.min(answer, getMinCost(1, rootColor, 0));
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** here을 color로 칠했을 때 이 subtree의 색칠 비용 최소값을 리턴 */
	public static long getMinCost(int here, int color, int parentNum) {
		// memoization
		if (cache[here][color] != CACHE_EMPTY) {
			return cache[here][color];
		}

		// 새로 칠해보기
		long totalSubTreeCost = 0L;
		for (int child : adjList[here]) {
			if (child != parentNum) {
				// 자식을 다른 색으로 칠하는 방법 중 최소 비용 구하기
				long subtreeCost = INF;
				for (int childColor = 1; childColor <= MAX_COLORS; childColor++) {
					if (color != childColor) {
						subtreeCost = Math.min(subtreeCost, getMinCost(child, childColor, here));
					}
				} // end for childColor

				// 그 최소 비용만큼을 합산
				totalSubTreeCost += subtreeCost;
			}

		} // end for child

		// here을 칠하는 비용 + 모든 children에 대한 서브트리를 칠하는 비용을 리턴
		return cache[here][color] = color + totalSubTreeCost;
	}
}