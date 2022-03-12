// 12156KB, 112ms

package bj10472;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int BOARD_SIZE = 3;
	static final boolean[][] whiteBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
	static final int DIRECTIONS = 5;
	static final int[] dy = { -1, 0, 0, 0, 1 };
	static final int[] dx = { 0, -1, 0, 1, 0 };
	static final int INF = 987654321;

	static boolean[][] board = new boolean[BOARD_SIZE][BOARD_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 입력
			for (int y = 0; y < BOARD_SIZE; y++) {
				char[] line = br.readLine().toCharArray();
				for (int x = 0; x < BOARD_SIZE; x++) {
					board[y][x] = (line[x] == '*') ? true : false;
				}
			}

			// 최소 클릭 횟수 계산
			int answer = getMinOperations();

			// 정답을 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
	}

	/** 최소 클릭 횟수를 계산해서 리턴 */
	public static int getMinOperations() {
		int ret = INF;

		for (int bitMask = 0; bitMask <= ((1 << 9) - 1); bitMask++) {
			// 비트마스크 정보대로 뒤집는다.
			for (int i = 0; i < 9; i++) {
				if ((bitMask & (1 << i)) != 0) {
					flip(i);
				}
			}

			// 흰 보드가 되었다면 뒤집은 횟수를 센다.
			if (isWhite()) {
				ret = Math.min(ret, Integer.bitCount(bitMask));
			}

			// 뒤집었던 것을 되돌린다
			for (int i = 0; i < 9; i++) {
				if ((bitMask & (1 << i)) != 0) {
					flip(i);
				}
			}

		}

		return ret;
	}

	/** board에서 i번 위치를 뒤집는다 */
	public static void flip(int i) {
		int centerY = i / BOARD_SIZE;
		int centerX = i % BOARD_SIZE;

		for (int dir = 0; dir < DIRECTIONS; dir++) {
			int y = centerY + dy[dir];
			int x = centerX + dx[dir];

			if (isInRange(y, x)) {
				board[y][x] = !board[y][x];
			}
		}
	}

	/** (y, x)가 보드 범위 내인지 여부를 리턴한다 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < BOARD_SIZE && 0 <= x && x < BOARD_SIZE) {
			return true;
		}

		return false;
	}

	/** 현재 board의 상태가 모두 white면 true, 아니면 false를 리턴한다 */
	public static boolean isWhite() {
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				if (board[y][x]) {
					return false;
				}
			}
		}

		return true;
	}
}