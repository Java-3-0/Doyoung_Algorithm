// 176608KB, 1380ms

package bj17276;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 테스트케이스 개수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		// 테스트케이스 수만큼 반복
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// N, D 입력
			st = new StringTokenizer(br.readLine(), " ");
			final int N = Integer.parseInt(st.nextToken());
			final int D = Integer.parseInt(st.nextToken());

			// 그리드 입력
			int[][] grid = new int[N][N];
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < N; x++) {
					grid[y][x] = Integer.parseInt(st.nextToken());
				}
			}

			// 실질 회전 각도 계산
			int netD = (D + 360) % 360;

			// 회전
			grid = rotate45clockwise(grid, netD / 45);

			// 결과를 출력 스트링빌더에 추가
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					sb.append(grid[y][x]).append(" ");
				}
				sb.append("\n");
			}
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 파라미터로 받은 grid를 45도 회전하는 것을 rotations번 한 2차원 배열을 리턴 */
	public static int[][] rotate45clockwise(int[][] grid, int rotations) {
		final int len = grid.length;
		int[][] ret = new int[len][len];

		// 안 돌아가는 칸들은 그대로 있어야 하므로, 일단 배열 전체를 복사함
		for (int y = 0; y < len; y++) {
			for (int x = 0; x < len; x++) {
				ret[y][x] = grid[y][x];
			}
		}

		int mid = len / 2;
		int last = len - 1;
		int[] startY = { 0, 0, 0, mid, last, last, last, mid };
		int[] startX = { 0, mid, last, last, last, mid, 0, 0 };
		int[] dy = { 1, 1, 1, 0, -1, -1, -1, 0 };
		int[] dx = { 1, 0, -1, -1, -1, 0, 1, 1 };

		// 한 줄씩 처리
		for (int idx = 0; idx < 8; idx++) {
			// grid의 한 줄을 45도씩 rotations번 돌아간 ret의 한 줄 위치에 넣음
			int nextIdx = (idx + rotations) % 8;
			for (int dist = 0; dist < len; dist++) {
				int toY = startY[nextIdx] + dist * dy[nextIdx];
				int toX = startX[nextIdx] + dist * dx[nextIdx];

				int fromY = startY[idx] + dist * dy[idx];
				int fromX = startX[idx] + dist * dx[idx];

				ret[toY][toX] = grid[fromY][fromX];
			}

		}

		return ret;
	}
}