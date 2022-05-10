// 20660KB, 284ms

package bj9577;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100, MAX_M = 100;
	static final int MAX_TIME = 100;
	static final int NOT_MATCHED = -1;

	static int N, M;
	// 간선은 시간 -> 조각 방향으로 존재
	static Set<Integer>[] adjList = new HashSet[MAX_TIME + 1];
	static int[] isMatchedTo = new int[MAX_N + 1];
	static boolean[] isChecked = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new HashSet<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			// 간선 정보 입력
			for (int person = 0; person < M; person++) {
				st = new StringTokenizer(br.readLine(), " ");
				int startTime = Integer.parseInt(st.nextToken());
				int endTime = Integer.parseInt(st.nextToken());
				int cntPieces = Integer.parseInt(st.nextToken());
				for (int k = 0; k < cntPieces; k++) {
					int piece = Integer.parseInt(st.nextToken());
					for (int time = startTime; time < endTime; time++) {
						adjList[time + 1].add(piece);
					}
				}
			}

			// 최대 매칭 수를 얻는 시간을 계산
			int minTime = getMinTimeToMatchAll();
			
			// 출력 스트링빌더에 추가
			sb.append(minTime).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isChecked, false);
		Arrays.fill(isMatchedTo, NOT_MATCHED);
	}

	/** 최대 매칭 수를 얻을 수 있는 시간을 리턴 */
	public static int getMinTimeToMatchAll() {
		for (int i = 0; i < isMatchedTo.length; i++) {
			Arrays.fill(isMatchedTo, NOT_MATCHED);
		}

		int ret = 0;
		for (int i = 0; i <= MAX_TIME; i++) {
			Arrays.fill(isChecked, false);
			if (tryMatch(i)) {
				ret++;
				if (ret == N) {
					return i;
				}
			}
		}

		return -1;
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