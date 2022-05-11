// 14816KB, 132ms

package bj1485;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static Point[] points = new Point[4];
	static int[] permu = new int[4];
	static boolean[] isUsed = new boolean[4];
	static long[] distanceSquares = new long[4];
	static boolean isPossible = false;

	static class Point {
		long x;
		long y;

		public Point(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			initMemory();

			for (int i = 0; i < 4; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				long x = Long.parseLong(st.nextToken());
				long y = Long.parseLong(st.nextToken());
				points[i] = new Point(x, y);
			}

			permutation(0);

			if (isPossible) {
				sb.append(1);
			} else {
				sb.append(0);
			}

			sb.append("\n");

		} // end for tc

		System.out.print(sb.toString());

	} // end main

	public static long getDistanceSquare(Point p1, Point p2) {
		long dx = p2.x - p1.x;
		long dy = p2.y - p1.y;
		long ret = dx * dx + dy * dy;
		return ret;
	}

	public static void initMemory() {
		Arrays.fill(points, null);
		Arrays.fill(permu, 0);
		Arrays.fill(isUsed, false);
		Arrays.fill(distanceSquares, 0L);
		isPossible = false;
	}

	public static void permutation(int idx) {
		if (idx == 4) {
			for (int i = 0; i < 4; i++) {
				Point now = points[permu[i % 4]];
				Point next = points[permu[(i + 1) % 4]];
				distanceSquares[i % 4] = getDistanceSquare(now, next);
			}

			long distSquare = distanceSquares[0];
			if (distanceSquares[1] == distSquare && distanceSquares[2] == distSquare && distanceSquares[3] == distSquare) {
				if (getDistanceSquare(points[permu[0]], points[permu[2]]) == 2L * distSquare) {
					isPossible = true;
				}
			}

			return;
		}

		for (int num = 0; num < 4; num++) {
			if (isUsed[num]) {
				continue;
			}

			isUsed[num] = true;
			permu[idx] = num;
			permutation(idx + 1);
			isUsed[num] = false;
		}

	}
}