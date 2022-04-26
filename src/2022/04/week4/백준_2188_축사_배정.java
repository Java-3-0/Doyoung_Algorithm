// 15596KB, 216ms

package bj2188;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000, MAX_M = 1000;

	static int N, M;
	/** 소 번호 -> 축사 번호로의 간선 정보 */
	static List<Integer>[] adjList;
	/** 각 축사를 확인했는지 여부를 나타내는 배열 */
	static boolean[] isChecked;
	/** 각 축사를 사용하는 소의 번호를 나타내는 배열 */
	static int[] isUsedBy;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		adjList = new ArrayList[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		isChecked = new boolean[M + 1];
		isUsedBy = new int[M + 1];

		// 소마다 원하는 축사 정보 입력
		for (int cow = 1; cow <= N; cow++) {
			st = new StringTokenizer(br.readLine(), " ");
			int cnt = Integer.parseInt(st.nextToken());
			for (int i = 0; i < cnt; i++) {
				int house = Integer.parseInt(st.nextToken());
				adjList[cow].add(house);
			}

			// 축사 번호 오름차순 정렬
			Collections.sort(adjList[cow]);
		}

		// 최대 매칭 가능한 소의 수 계산
		int answer = getMaxMatches();

		// 출력
		System.out.println(answer);

	} // end main

	/** 현재 adjList 상태에서 가능한 최대 축사 배정 개수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		// 각 소를 축사에 배정하는 것을 시도한다
		for (int cow = 1; cow <= N; cow++) {
			Arrays.fill(isChecked, false);

			if (dfs(cow)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 소와 축사의 매칭 시도 */
	public static boolean dfs(int cow) {
		for (int house : adjList[cow]) {
			if (!isChecked[house]) {
				isChecked[house] = true;
				// 빈 축사이거나, 축사에 있는 소를 다른 축사로 옮겨줄 수 있는 경우
				if (isUsedBy[house] == 0 || dfs(isUsedBy[house])) {
					isUsedBy[house] = cow;
					return true;
				}
			}
		}

		return false;
	}

}