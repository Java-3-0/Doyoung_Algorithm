// 13184KB, 84ms

package bj2583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 100, MAX_W = 100;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int H, W, K;
	static boolean[][] isFilled = new boolean[MAX_H][MAX_W];
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		initMemory();

		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			for (int y = y1; y < y2; y++) {
				for (int x = x1; x < x2; x++) {
					isFilled[y][x] = true;
				}
			}
		}

		int numGroups = 0;
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (!isFilled[y][x] && !isVisited[y][x]) {
					isVisited[y][x] = true;
					int groupSize = dfs(y, x);
					pq.offer(groupSize);
					numGroups++;
				}
			}
		}

		sb.append(numGroups).append("\n");
		
		while (!pq.isEmpty()) {
			sb.append(pq.poll()).append(" ");
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

	public static int dfs(int y, int x) {
		int ret = 1;
		
		for (int dir = 0; dir < dy.length; dir++) {
			int ny = y + dy[dir];
			int nx = x + dx[dir];

			if (isInRange(ny, nx) && !isFilled[ny][nx] && !isVisited[ny][nx]) {
				isVisited[ny][nx] = true;
				ret += dfs(ny, nx);
			}
		}
		
		return ret;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}
		return false;
	}

	public static void initMemory() {
		for (int i = 0; i < isFilled.length; i++) {
			Arrays.fill(isFilled[i], false);
		}
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}