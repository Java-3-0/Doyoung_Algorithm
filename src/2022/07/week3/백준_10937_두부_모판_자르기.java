// 15492KB, 104ms

package boj10937;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 11;
	static final int MAX_BITMASK = (1 << MAX_N) - 1;
	static final int CACHE_EMPTY = -1;

	static int[][] scores = new int[4][4];
	static int N;
	static char[][] grid = new char[MAX_N][MAX_N];
	static int[][] cache = new int[MAX_N * MAX_N + 1][MAX_BITMASK + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 점수 테이블 초기화
		initScores();

		// 캐시 초기화
		initCache();

		// 격자판 크기 입력
		N = Integer.parseInt(br.readLine());

		// 격자판 입력
		for (int y = 0; y < N; y++) {
			String line = br.readLine();
			for (int x = 0; x < N; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		// 정답 계산
		int answer = solve(0, 0);

		System.out.println(answer);

	} // end main

	public static void initScores() {
		scores[0][0] = 100;
		scores[0][1] = 70;
		scores[0][2] = 40;
		scores[0][3] = 0;

		scores[1][0] = 70;
		scores[1][1] = 50;
		scores[1][2] = 30;
		scores[1][3] = 0;

		scores[2][0] = 40;
		scores[2][1] = 30;
		scores[2][2] = 20;
		scores[2][3] = 0;

		scores[3][0] = 0;
		scores[3][1] = 0;
		scores[3][2] = 0;
		scores[3][3] = 0;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	public static int getScore(char grade1, char grade2) {
		int idx1 = Math.min(grade1 - 'A', 3);
		int idx2 = Math.min(grade2 - 'A', 3);

		return scores[idx1][idx2];
	}

	/** idx는 현재 채울 칸의 위치, used는 현재 칸부터 W개의 칸의 사용 정보로 주어졌을 때 가능한 최대 점수를 리턴 */
	public static int solve(int idx, int used) {
		// 1. base case
		if (idx == N * N) {
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
		int y = idx / N;
		int x = idx % N;

		char gradeHere = grid[y][x];

		// 아래랑 묶는 경우
		if (y + 1 < N) {
			char gradeDown = grid[y + 1][x];
			int scoreHere = getScore(gradeHere, gradeDown);
			int scoreDown = solve(idx + 1, (used >> 1) | (1 << (N - 1)));
			ret = Math.max(ret, scoreHere + scoreDown);
		}

		// 오른쪽과 묶는 경우
		if (x + 1 < N && (used & (1 << 1)) == 0) {
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