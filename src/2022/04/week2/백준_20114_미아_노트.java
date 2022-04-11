// 11540KB, 84ms

package bj20114;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int H;
	static int W;
	static char[][] grid;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());

		grid = new char[H][N * W];
		for (int y = 0; y < H; y++) {
			String line = br.readLine();
			for (int x = 0; x < N * W; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		char[] original = new char[N];
		Arrays.fill(original, '?');

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < N; x++) {
				for (int k = 0; k < W; k++) {
					char c = grid[y][W * x + k];

					if (c != '?') {
						original[x] = c;
					}
				}

			}
		}

		for (char c : original) {
			sb.append(c);
		}

		System.out.println(sb.toString());

	} // end main

}