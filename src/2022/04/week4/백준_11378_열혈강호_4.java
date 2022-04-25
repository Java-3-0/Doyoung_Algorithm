// 51424KB, 3152ms

package bj11378;

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

	static int N, M, K;
	/** 사람 번호 -> 일 번호로의 간선 정보 */
	static List<Integer>[] adjList;
	/** 각 일을 확인했는지 여부를 나타내는 배열 */
	static boolean[] isChecked;
	/** 각 일을 담당하는 사람의 번호를 나타내는 배열 */
	static int[] isDoneBy;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		adjList = new ArrayList[N + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		isChecked = new boolean[M + 1];
		isDoneBy = new int[M + 1];

		// 직원마다 할 수 있는 일 정보 입력
		for (int person = 1; person <= N; person++) {
			st = new StringTokenizer(br.readLine(), " ");
			int cnt = Integer.parseInt(st.nextToken());
			for (int i = 0; i < cnt; i++) {
				int job = Integer.parseInt(st.nextToken());
				adjList[person].add(job);
			}

			// 일 번호 오름차순 정렬
			Collections.sort(adjList[person]);
		}

		// 최대 할 수 있는 일 개수 계산
		int answer = getMaxJobs();

		// 출력
		System.out.println(answer);

	} // end main

	/** 현재 adjList 상태에서 가능한 최대 일 개수를 구해서 리턴 */
	public static int getMaxJobs() {
		int ret = 0;

		// 벌점이 없더라도, 모든 사람은 1개씩의 일은 시도할 수 있다
		for (int person = 1; person <= N; person++) {
			Arrays.fill(isChecked, false);

			if (dfs(person)) {
				ret++;
			}
		}
		
		// 벌점을 어떻게 부여하는지에 따라 K개의 일을 추가로 할 수 있다.
		int cnt = 0;
		OUTER: for (int person = 1; person <= N; person++) {
			Arrays.fill(isChecked, false);

			while (dfs(person)) {
				ret++;
				cnt++;
				if (cnt == K) {
					break OUTER;
				}
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 일과의 매칭 시도 */
	public static boolean dfs(int person) {
		for (int job : adjList[person]) {
			if (!isChecked[job]) {
				isChecked[job] = true;
				// 아무도 담당하지 않던 일이거나, 이미 담당하던 사람을 다른 일로 배정해 줄 수 있는 경우
				if (isDoneBy[job] == 0 || dfs(isDoneBy[job])) {
					isDoneBy[job] = person;
					return true;
				}
			}
		}

		return false;
	}

}