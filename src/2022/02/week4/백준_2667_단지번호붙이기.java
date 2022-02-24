// 11664KB, 76ms

package bj2667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 25;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int grid[][] = new int[MAX_N][MAX_N];
	static boolean isVisited[][] = new boolean[MAX_N][MAX_N];
	static List<Integer> houseCounts = new ArrayList<>();
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		for (int y = 0; y < N; y++) {
			String line = br.readLine();
			for (int x = 0; x < N; x++) {
				grid[y][x] = line.charAt(x) - '0';
			}
		}

		for (int startY = 0; startY < N; startY++) {
			for (int startX = 0; startX < N; startX++) {
				if (!isVisited[startY][startX] && grid[startY][startX] == 1) {
					houseCounts.add(dfs(startY, startX));
				}
			}
		}

		Collections.sort(houseCounts);

		sb.append(houseCounts.size()).append("\n");
		for (int count : houseCounts) {
			sb.append(count).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static int dfs(int startY, int startX) {
		if (isVisited[startY][startX]) {
			return 0;
		}

		isVisited[startY][startX] = true;
		int ret = 1;
		for (int dir = 0; dir < dy.length; dir++) {
			int nextY = startY + dy[dir];
			int nextX = startX + dx[dir];

			if (isInRange(nextY, nextX) && grid[nextY][nextX] == 1) {
				ret += dfs(nextY, nextX);
			}
		}

		return ret;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < N && 0 <= x && x < N) {
			return true;
		}

		return false;
	}
}