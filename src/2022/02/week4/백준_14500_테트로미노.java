// 38476KB, 596ms

package bj14500;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int[][] dy = { { 0, 0, 0, 0 }, { 0, 0, 1, 1 }, { 0, 1, 2, 2 }, { 0, 1, 1, 2 }, { 0, 0, 0, 1 } };

	static int[][] dx = { { 0, 1, 2, 3 }, { 0, 1, 0, 1 }, { 0, 0, 0, 1 }, { 0, 0, 1, 1 }, { 0, 1, 2, 1 } };

	static int maxSum = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());

		// 그리드 입력
		int[][] grid = new int[height][width];
		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 모양이 다른 테트로미노를 각각 회전시키고 뒤집는 것은 복잡하니, 테트로미노들은 그대로 두고 종이를 돌리고 뒤집으면서 테트로미노를 놓아본다.
		for (int r = 0; r < 4; r++) {
			grid = rotate(grid);
			maxSum = Math.max(maxSum, getMaxSum(grid));
		}

		grid = flip(grid);

		for (int r = 0; r < 4; r++) {
			grid = rotate(grid);
			maxSum = Math.max(maxSum, getMaxSum(grid));
		}

		// 구해진 최대 합계 출력
		System.out.println(maxSum);

	} // end main

	/** 파라미터로 주어진 arr을 시계방향으로 90도 회전시킨 배열을 리턴 */
	public static int[][] rotate(int[][] arr) {
		int h = arr.length;
		int w = arr[0].length;

		int[][] ret = new int[w][h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				ret[x][h - 1 - y] = arr[y][x];
			}
		}

		return ret;
	}

	/** 파라미터로 주어진 arr을 y축을 기준으로 뒤집은 배열을 리턴 */
	public static int[][] flip(int[][] arr) {
		int h = arr.length;
		int w = arr[0].length;

		int[][] ret = new int[h][w];

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				ret[y][w - 1 - x] = arr[y][x];
			}
		}

		return ret;
	}

	/** 그리드 상태가 주어질 때, 테트로미노를 놓아서 얻을 수 있는 최대 합계를 리턴 */
	public static int getMaxSum(int[][] grid) {
		int h = grid.length;
		int w = grid[0].length;

		int ret = Integer.MIN_VALUE;
		
		// 모든 시작 위치에 모든 테트로미노를 놓아 본다.
		for (int startY = 0; startY < h; startY++) {
			for (int startX = 0; startX < w; startX++) {
				tetroLoop: for (int tetroType = 0; tetroType < 5; tetroType++) {
					int sum = 0;
					for (int square = 0; square < 4; square++) {
						int y = startY + dy[tetroType][square];
						int x = startX + dx[tetroType][square];
						
						// 범위 내라면 더한다
						if (0 <= y && y < h && 0 <= x && x < w) {
							sum += grid[y][x];
						} 
						
						// 범위 밖이라면 이 테트로미노를 놓을 수 없으니 다음 테트로미노로 진행한다
						else {
							continue tetroLoop;
						}
					}
					
					// 누적된 값으로 최대값을 갱신한다.
					ret = Math.max(ret, sum);
				}

			}
		}

		return ret;
	}
}