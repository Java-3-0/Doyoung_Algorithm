// 12188KB, 92ms

package bj25243;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_R = 200, MAX_C = 200;
	static final int IDOLS = 3;

	static int[][] grid;

	static class Position implements Comparable<Position> {
		int pos;
		int score;

		public Position(int pos, int score) {
			super();
			this.pos = pos;
			this.score = score;
		}

		@Override
		public String toString() {
			return "Position [pos=" + pos + ", score=" + score + "]";
		}

		@Override
		public int compareTo(Position p) {
			return -(this.score - p.score);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int RC = R * C;

		// 삼총사 번호 입력
		br.readLine();

		// 인접할 때의 보너스 점수 입력
		int[] bonuses = new int[IDOLS];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < IDOLS; i++) {
			bonuses[i] = Integer.parseInt(st.nextToken());
		}

		// 그리드 입력
		int[][] grid = new int[R][C];
		List<Position> scores = new ArrayList<>();
		for (int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int c = 0; c < C; c++) {
				int input = Integer.parseInt(st.nextToken());
				grid[r][c] = input;
				int pos = r * C + c;
				scores.add(new Position(pos, input));
			}
		}

		// 내림차순 정렬
		Collections.sort(scores);

		// 가장 큰 수 N개를 더한다
		int scoresTopN = 0;
		boolean[] isUsed = new boolean[RC];
		for (int i = 0; i < N; i++) {
			scoresTopN += scores.get(i).score;
			isUsed[scores.get(i).pos] = true;
		}

		// 3명으로 최대한의 점수를 만든다
		int maxScore = 0;
		for (int p1 = 0; p1 < RC; p1++) {
			for (int p2 = 0; p2 < RC; p2++) {
				if (p2 == p1) {
					continue;
				}

				for (int p3 = 0; p3 < RC; p3++) {
					if (p3 == p1 || p3 == p2) {
						continue;
					}

					int r1 = p1 / C;
					int c1 = p1 % C;

					int r2 = p2 / C;
					int c2 = p2 % C;

					int r3 = p3 / C;
					int c3 = p3 % C;

					int basicScore = scoresTopN;
					int notUsedCnt = 0;
					if (!isUsed[p1]) {
						basicScore += grid[r1][c1];
						notUsedCnt++;
					}
					if (!isUsed[p2]) {
						basicScore += grid[r2][c2];
						notUsedCnt++;
					}
					if (!isUsed[p3]) {
						basicScore += grid[r3][c3];
						notUsedCnt++;
					}

					for (int i = N - 1; i >= 0; i--) {
						if (notUsedCnt == 0) {
							break;
						}
						int pos = scores.get(i).pos;

						if (pos != p1 && pos != p2 && pos != p3) {
							basicScore -= scores.get(i).score;
							notUsedCnt--;
						}
					}

					int bonusScore = 0;
					if (isAdjacent(r1, c1, r2, c2)) {
						bonusScore += bonuses[0];
					}
					if (isAdjacent(r2, c2, r3, c3)) {
						bonusScore += bonuses[1];
					}
					if (isAdjacent(r3, c3, r1, c1)) {
						bonusScore += bonuses[2];
					}

					int totalScore = basicScore + bonusScore;

					if ((maxScore < totalScore)) {
						maxScore = totalScore;
					}

				} // end for p3

			} // end for p2

		} // end for p1

		System.out.println(maxScore);

	} // end main

	public static boolean isAdjacent(int ra, int ca, int rb, int cb) {
		int dr = Math.abs(rb - ra);
		int dc = Math.abs(cb - ca);

		if (dr <= 1 && dc <= 1) {
			return true;
		}

		return false;
	}

}