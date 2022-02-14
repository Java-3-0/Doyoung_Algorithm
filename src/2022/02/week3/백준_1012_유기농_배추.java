// 14304KB, 116ms

package bj1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Position {
	int y;
	int x;

	public Position(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	public boolean isInRange() {
		if (0 <= y && y < Main.N && 0 <= x && x < Main.M) {
			return true;
		}
		return false;
	}
}

public class Main {
	public static final int MAX_M = 50;
	public static final int MAX_N = 50;
	public static final int MAX_K = 2500;

	public static final int[] dy = { 1, -1, 0, 0 };
	public static final int[] dx = { 0, 0, -1, 1 };

	public static int M, N, K;
	public static int[][] field = new int[MAX_N][MAX_N];
	public static boolean[][] isVisited = new boolean[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			// M, N, K 입력
			st = new StringTokenizer(br.readLine(), " ");
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// field 초기화
			for (int i = 0; i < field.length; i++) {
				Arrays.fill(field[i], 0);
			}

			// 각 배추의 위치 입력받아서 field 갱신
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				field[y][x] = 1;
			}

			// 정답 계산 및 출력
			int answer = countZones();
			sb.append(answer).append("\n");
		} // end for testCase

		System.out.print(sb.toString());
	} // end main

	/** bfs를 몇 번 수행해야 모든 배추 땅을 탐색하는지 리턴 */
	public static int countZones() {
		int ret = 0;

		// isVisited 초기화
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}

		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				// 방문한 곳이면 패스
				if (isVisited[y][x])
					continue;

				// 방문하지 않은 배추 없는 땅이면 방문 여부만 갱신하고 패스
				if (field[y][x] == 0) {
					isVisited[y][x] = true;
					continue;
				}

				// 방문하지 않은 배추 있는 칸이면 연결된 배추 칸 모두 탐색
				bfs(y, x);
				ret++;
			}
		}

		return ret;
	}
	
	/** bfs를 통해 (y, x)부터 연결된 배추 영역들을 탐색 */
	public static void bfs(int y, int x) {
		Queue<Position> q = new LinkedList<>();
		q.offer(new Position(y, x));

		while (!q.isEmpty()) {
			Position here = q.poll();
			if (isVisited[here.y][here.x]) {
				continue;
			}

			isVisited[here.y][here.x] = true;
			for (int dir = 0; dir < 4; dir++) {
				Position there = new Position(here.y + dy[dir], here.x + dx[dir]);
				if (there.isInRange() && field[there.y][there.x] == 1) {
					q.offer(there);
				}
			}
		}
	}
}
