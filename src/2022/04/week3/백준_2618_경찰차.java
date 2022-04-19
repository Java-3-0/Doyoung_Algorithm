// 21632KB, 180ms

package bj2618;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000, MAX_W = 1000;
	static final int CACHE_EMPTY = -1;

	static int N, W;
	static Position[] positions = new Position[MAX_W + 1];
	static Position start1, start2;

	/** cache[i][j] : 경찰차 1의 마지막 담당 사건이 i, 경찰차 2의 마지막 담당 사건이 j일 때, 앞으로 가능한 최소 거리 */
	static int[][] cache = new int[MAX_W + 1][MAX_W + 1];

	/** 각 사건을 담당한 경찰차 번호 */
	static int[][] choices = new int[MAX_W + 1][MAX_W + 1];

	static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getDistanceTo(Position p) {
			return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 캐시 초기화
		initCache();

		// N, W 입력
		N = Integer.parseInt(br.readLine());
		W = Integer.parseInt(br.readLine());

		// 경찰차 첫 위치 설정
		start1 = new Position(1, 1);
		start2 = new Position(N, N);

		// 사건 위치 입력
		for (int i = 1; i <= W; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 최단 거리 계산하고, 각 사건의 담당 경찰차도 handledBy[]에 담아둔다
		int minDist = getMinDistance(0, 0);

		// 출력 스트링빌더에 최단 거리 추가
		sb.append(minDist).append("\n");

		// 출력 스트링빌더에 담당 경찰차들 추가
		int last1 = 0;
		int last2 = 0;
		for (int i = 1; i <= W; i++) {
			int pick = choices[last1][last2];
			sb.append(pick).append("\n");

			if (pick == 1) {
				last1 = i;
			} else {
				last2 = i;
			}
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** 경찰차 1이 마지막으로 담당한 사건 번호, 경찰차 2가 마지막으로 담당한 사건 번호가 주어졌을 때, 앞으로 가능한 최소 거리를 리턴 */
	public static int getMinDistance(int last1, int last2) {
		// 이번에 처리할 사건 번호
		int curIdx = Math.max(last1, last2) + 1;

		// base case : 모든 사건을 처리한 경우
		if (curIdx > W) {
			return 0;
		}

		// 이미 캐시에 저장되어 있는 경우
		if (cache[last1][last2] != CACHE_EMPTY) {
			return cache[last1][last2];
		}

		// 새로 계산해서 캐시에 저장하는 경우

		// 현재 경찰차 위치 계산
		Position pos1 = (last1 == 0) ? start1 : positions[last1];
		Position pos2 = (last2 == 0) ? start2 : positions[last2];

		// 각 경찰차가 이번 사건 위치로 이동하는 경우 중 더 나은 쪽 선택
		Position curPos = positions[curIdx];

		int dist1 = pos1.getDistanceTo(curPos) + getMinDistance(curIdx, last2);
		int dist2 = pos2.getDistanceTo(curPos) + getMinDistance(last1, curIdx);

		int ret = 0;

		if (dist1 < dist2) {
			ret = dist1;
			choices[last1][last2] = 1;
		} else {
			ret = dist2;
			choices[last1][last2] = 2;
		}

		return cache[last1][last2] = ret;
	}

}