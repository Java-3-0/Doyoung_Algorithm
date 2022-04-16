// 58264KB, 1448ms

package bj13565;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W;
	static int[][] grid;
	static boolean[][] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		grid = new int[H][W];
		isVisited = new boolean[H][W];

		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < W; x++) {
				grid[y][x] = line.charAt(x) - '0';
			}
		}

		for (int x = 0; x < W; x++) {
			if (!isVisited[0][x] && grid[0][x] == 0) {
				dfs(0, x);
			}

		}

		String answer = isSuccess() ? "YES" : "NO";

		System.out.println(answer);

	} // end main

	public static boolean isSuccess() {
		for (int x = 0; x < W; x++) {
			if (isVisited[H - 1][x]) {
				return true;
			}
		}

		return false;
	}

	public static void dfs(int startY, int startX) {
		if (isVisited[startY][startX]) {
			return;
		}

		isVisited[startY][startX] = true;

		for (int dir = 0; dir < dy.length; dir++) {
			int nextY = startY + dy[dir];
			int nextX = startX + dx[dir];
			if (isInRange(nextY, nextX) && grid[nextY][nextX] == 0) {
				dfs(nextY, nextX);
			}
		}

		return;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}
}