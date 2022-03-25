// 21364KB, 196ms

package bj2508;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_SIZE = 400;

	static int height, width;
	static int[][] grid = new int[MAX_SIZE][MAX_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			br.readLine();
			st = new StringTokenizer(br.readLine(), " ");
			height = Integer.parseInt(st.nextToken());
			width = Integer.parseInt(st.nextToken());

			for (int y = 0; y < height; y++) {
				char[] line = br.readLine().toCharArray();
				for (int x = 0; x < width; x++) {
					grid[y][x] = line[x];
				}
			}

			int answer = getCandyCount();

			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	public static int getCandyCount() {
		int ret = 0;
		for (int y = 0; y < height - 2; y++) {
			for (int x = 0; x < width; x++) {
				if (grid[y][x] == 'v' && grid[y + 1][x] == 'o' && grid[y + 2][x] == '^') {
					ret++;
				}
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width - 2; x++) {
				if (grid[y][x] == '>' && grid[y][x + 1] == 'o' && grid[y][x + 2] == '<') {
					ret++;
				}
			}
		}

		return ret;
	}

}