// 18812KB, 204ms

package bj16956;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 500, MAX_W = 500;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W;
	static char[][] grid = new char[MAX_H][MAX_W];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		for (int y = 0; y < H; y++) {
			char[] line = br.readLine().toCharArray();
			for (int x = 0; x < W; x++) {
				char input = line[x];
				if (input == '.') {
					grid[y][x] = 'D';
				} else {
					grid[y][x] = input;
				}
			}
		}

		int canBlock = canMeetSheep() ? 0 : 1;
		sb.append(canBlock).append("\n");
		if (canBlock == 1) {
			for (int y = 0; y < H; y++) {
				for (int x = 0; x < W; x++) {
					sb.append(grid[y][x]);
				}
				sb.append("\n");
			}
		}

		System.out.print(sb.toString());

	} // end main

	public static boolean canMeetSheep() {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (grid[y][x] == 'W') {
					for (int dir = 0; dir < dy.length; dir++) {
						int ny = y + dy[dir];
						int nx = x + dx[dir];

						if (isInRange(ny, nx) && grid[ny][nx] == 'S') {
							return true;
						}
					}

				}
			}
		}

		return false;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}
}