// 12472KB, 100ms

package bj14433;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300, MAX_M = 300, MAX_K = 500;
	static final int NOT_MATCHED = -1;

	static int N, M, K1, K2;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] isMatchedTo = new int[MAX_M + 1];
	static boolean[] isChecked = new boolean[MAX_M + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// N, M, K1, K2 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K1 = Integer.parseInt(st.nextToken());
		K2 = Integer.parseInt(st.nextToken());

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 우리 팀 간선 정보 입력
		initMemory();
		for (int i = 0; i < K1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
		}

		// 우리 팀 간선 정보 오름차순 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}

		// 우리 팀 최대 트롤 수 계산
		int myTeamTrolls = getMaxMatches();

		// 적 팀 간선 정보 입력
		initMemory();
		for (int i = 0; i < K2; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
		}

		// 적 팀 간선 정보 오름차순 정렬
		for (int i = 0; i < adjList.length; i++) {
			Collections.sort(adjList[i]);
		}

		// 적 팀 최대 트롤 수 계산
		int enemyTeamTrolls = getMaxMatches();

		// 출력
		if (myTeamTrolls < enemyTeamTrolls) {
			System.out.println("네 다음 힐딱이");
		} else {
			System.out.println("그만 알아보자");
		}

	} // end main

	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isChecked, false);
		Arrays.fill(isMatchedTo, NOT_MATCHED);
	}

	/** 최대 매칭 수를 리턴 */
	public static int getMaxMatches() {
		for (int i = 0; i < isMatchedTo.length; i++) {
			Arrays.fill(isMatchedTo, NOT_MATCHED);
		}

		int ret = 0;
		for (int i = 1; i <= N; i++) {
			Arrays.fill(isChecked, false);
			if (tryMatch(i)) {
				ret++;
			}
		}

		return ret;
	}

	/** i번 인덱스의 매칭을 시도하고 성공 시 true, 실패 시 false를 리턴 */
	public static boolean tryMatch(int x) {
		for (int y : adjList[x]) {
			if (isChecked[y]) {
				continue;
			}
			isChecked[y] = true;

			if (isMatchedTo[y] == NOT_MATCHED || tryMatch(isMatchedTo[y])) {
				isMatchedTo[y] = x;
				return true;
			}
		}

		return false;
	}
}