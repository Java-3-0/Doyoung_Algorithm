// 19688KB, 282ms

package bj10163;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int GRID_SIZE = 1001;
	static int[][] grid = new int[GRID_SIZE][GRID_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		initGrid();
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int startX = Integer.parseInt(st.nextToken());
			int startY = Integer.parseInt(st.nextToken());
			int width = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());

			for (int dy = 0; dy < height; dy++) {
				int y = startY + dy;
				for (int dx = 0; dx < width; dx++) {
					int x = startX + dx;

					grid[y][x] = i;
				}
			}
		}

		int[] counts = new int[N + 1];
		Arrays.fill(counts, 0);
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				counts[grid[y][x]]++;
			}
		}

		for (int i = 1; i <= N; i++) {
			sb.append(counts[i]).append("\n");
		}

		System.out.print(sb.toString());

	}

	public static void initGrid() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], 0);
		}
	}

}