// 27980KB, 212ms

package boj1657;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 14, MAX_W = 14;
	static final int MAX_BITMASK = (1 << MAX_W) - 1;
	static final int CACHE_EMPTY = -1;

	static int[][] scores = new int[5][5];
	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
	static int[][] cache = new int[MAX_H * MAX_W + 1][MAX_BITMASK + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 점수 테이블 초기화
		initScores();

		// 캐시 초기화
		initCache();

		// 격자판 크기 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		// 격자판 입력
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		// 정답 계산
		int answer = solve(0, 0);

		// 출력
		System.out.println(answer);

	} // end main

	public static void initScores() {
		scores[0][0] = 10;
		scores[0][1] = 8;
		scores[0][2] = 7;
		scores[0][3] = 5;
		scores[0][4] = 1;

		scores[1][0] = 8;
		scores[1][1] = 6;
		scores[1][2] = 4;
		scores[1][3] = 3;
		scores[1][4] = 1;

		scores[2][0] = 7;
		scores[2][1] = 4;
		scores[2][2] = 3;
		scores[2][3] = 2;
		scores[2][4] = 1;

		scores[3][0] = 5;
		scores[3][1] = 3;
		scores[3][2] = 2;
		scores[3][3] = 2;
		scores[3][4] = 1;

		scores[4][0] = 1;
		scores[4][1] = 1;
		scores[4][2] = 1;
		scores[4][3] = 1;
		scores[4][4] = 0;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getScore(char grade1, char grade2) {
		int idx1 = Math.min(grade1 - 'A', 4);
		int idx2 = Math.min(grade2 - 'A', 4);

		return scores[idx1][idx2];
	}

	/** idx는 현재 채울 칸의 위치, used는 현재 칸부터 W개의 칸의 사용 정보로 주어졌을 때 가능한 최대 점수를 리턴 */
	public static int solve(int idx, int used) {
		// 1. base case
		if (idx == H * W) {
			return 0;
		}

		// 2. memoization
		if (cache[idx][used] != CACHE_EMPTY) {
			return cache[idx][used];
		}

		// 3-1. 이미 채운 칸인 경우
		if ((used & 1) != 0) {
			return cache[idx][used] = solve(idx + 1, (used >> 1));
		}

		// 3-2. 새로 채우는 경우
		int ret = 0;

		// 좌표 계산
		int y = idx / W;
		int x = idx % W;

		char gradeHere = grid[y][x];

		// 아래랑 묶는 경우
		if (y + 1 < H) {
			char gradeDown = grid[y + 1][x];
			int scoreHere = getScore(gradeHere, gradeDown);
			int scoreDown = solve(idx + 1, (used >> 1) | (1 << (W - 1)));
			ret = Math.max(ret, scoreHere + scoreDown);
		}

		// 오른쪽과 묶는 경우
		if (x + 1 < W && (used & (1 << 1)) == 0) {
			char gradeRight = grid[y][x + 1];
			int scoreHere = getScore(gradeHere, gradeRight);
			int scoreRight = solve(idx + 1, (used >> 1) | 1);
			ret = Math.max(ret, scoreHere + scoreRight);
		}

		// 안 묶는 경우
		ret = Math.max(ret, solve(idx + 1, (used >> 1)));
		
		return cache[idx][used] = ret;
	}
}