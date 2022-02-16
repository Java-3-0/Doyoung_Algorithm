// 64364KB, 1660ms

package bj9184;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int EMPTY = -987654321;
	public static int[][][] cache = new int[20 + 1][20 + 1][20 + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		while (true) {
			for (int a = 0; a < cache.length; a++) {
				for (int b = 0; b < cache[a].length; b++) {
					Arrays.fill(cache[a][b], EMPTY);
				}
			}

			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			if (a == -1 && b == -1 && c == -1) {
				break;
			}

			sb.append("w(").append(a).append(", ").append(b).append(", ").append(c).append(") = ").append(w(a, b, c))
					.append("\n");
		}

		System.out.print(sb.toString());

	}

	public static int w(int a, int b, int c) {
		if (a <= 0 || b <= 0 || c <= 0)
			return 1;
		if (a > 20 || b > 20 || c > 20)
			return w(20, 20, 20);

		if (cache[a][b][c] != EMPTY) {
			return cache[a][b][c];
		}

		if (a < b && b < c) {
			return cache[a][b][c] = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c);
		}

		return cache[a][b][c] = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1);

	}
}