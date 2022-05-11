// 17335KB, 172ms

package bj11123;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100, MAX_W = 100;
	static final char SHEEP = '#', EMPTY = '.';
	static final int[] dy = {-1, 1, 0, 0};
	static final int[] dx = {0, 0, -1, 1};

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			initMemory();

			st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			for (int y = 0; y < H; y++) {
				String line = br.readLine();
				for (int x = 0; x < W; x++) {
					grid[y][x] = line.charAt(x);
				}
			}
			
			int answer = 0;
			for (int y = 0; y < H; y++) {
				for (int x = 0; x < W; x++) {
					if (grid[y][x] == SHEEP && !isVisited[y][x]) {
						isVisited[y][x] = true;
						dfs(y, x);
						answer++;
					}
				}
			}
			
			sb.append(answer).append("\n");
			
		} // end for tc
		
		System.out.print(sb.toString());

	} // end main

	public static void dfs(int y, int x) {
		for (int dir = 0; dir < dy.length; dir++) {
			int ny = y + dy[dir];
			int nx = x + dx[dir];
			
			if (isInRange(ny, nx) && grid[ny][nx] == SHEEP && !isVisited[ny][nx]) {
				isVisited[ny][nx] = true;
				dfs(ny, nx);
			}
		}
	}
	
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}

	public static void initMemory() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], '0');
		}
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}