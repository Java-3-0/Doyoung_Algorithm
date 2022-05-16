// 15292KB, 104ms

package bj2210;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int GRID_SIZE = 5;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int[][] grid = new int[GRID_SIZE][GRID_SIZE];
	static Set<Integer> possibles = new HashSet<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int y = 0; y < GRID_SIZE; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < GRID_SIZE; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				dfs(y, x, 0, 0);
			}
		}
		
		int answer = possibles.size();

		System.out.println(answer);

	} // end main

	public static void dfs(int y, int x, int accum, int depth) {
		if (depth == 6) {
			possibles.add(accum);
			return;
		}
		
		for (int dir = 0; dir < dy.length; dir++) {
			int ny = y + dy[dir];
			int nx = x + dx[dir];
			
			if (isInRange(ny, nx)) {
				dfs(ny, nx, accum * 10 + grid[y][x], depth + 1);
			}
		}
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < GRID_SIZE && 0 <= x && x < GRID_SIZE) {
			return true;
		}
		return false;
	}
}