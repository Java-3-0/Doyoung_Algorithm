// 16540KB, 124ms

package bj14620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	/** 그리드 최대 크기 */
	static final int MAX_N = 10;
	/** 꽃잎 모양 */
	static final int[] dy = { -1, 0, 0, 0, 1 };
	static final int[] dx = { 0, -1, 0, 1, 0 };

	/** 그리드 크기 */
	static int N;
	/** 그리드 각 위치에서의 화단 대여 가격 */
	static int[][] prices = new int[MAX_N][MAX_N];
	/** 선택할 세 지점의 좌표들 */
	static int[][] combi = new int[3][2];
	/** 완전탐색할 시작 수, 끝 수 */
	static int firstNum, lastNum;
	/** 구하려는 최소 비용 */
	static int minCost = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		N = Integer.parseInt(br.readLine());
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				prices[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 모든 조합 완전탐색
		firstNum = N * 1 + 1;
		lastNum = N * (N - 2) + (N - 2);
		combination(N + 1, 0);

		// minCost 출력
		System.out.println(minCost);
	}

	/** 모든 조합을 완전탐색하면서 minCost 갱신 */
	public static void combination(int startNum, int cnt) {
		// 조합이 완성된 경우
		if (cnt == 3) {
			for (int idx = 0; idx < 3; idx++) {
				// 각 씨앗 위치 사이의 거리 계산
				int nextIdx = (idx + 1) % 3;
				int distY = Math.abs(combi[nextIdx][0] - combi[idx][0]);
				int distX = Math.abs(combi[nextIdx][1] - combi[idx][1]);
				int dist = distY + distX;

				// 꽃잎이 겹치는 경우
				if (dist <= 2) {
					return;
				}
			}

			// 비용 계산
			int cost = 0;
			for (int idx = 0; idx < 3; idx++) {
				for (int dir = 0; dir < 5; dir++) {
					int y = combi[idx][0] + dy[dir];
					int x = combi[idx][1] + dx[dir];
					cost += prices[y][x];
				}
			}

			// 최소 비용 갱신
			minCost = cost < minCost ? cost : minCost;

			return;
		}

		// 조합 만들기
		for (int pick = startNum; pick <= lastNum; pick++) {
			int y = pick / N;
			int x = pick % N;
			if (y == 0 || y == N - 1 || x == 0 || x == N - 1) {
				continue;
			}

			combi[cnt][0] = pick / N;
			combi[cnt][1] = pick % N;
			combination(pick + 1, cnt + 1);
		}

	}
}