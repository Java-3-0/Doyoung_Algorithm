// 370968KB, 1616ms

package bj14442;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_HEIGHT = 1000;
	public static final int MAX_WIDTH = 1000;
	public static final int MAX_K = 10;
	
	public static final int DIRECTIONS = 4;
	public static final int[] dy = { 0, 1, 0, -1 };
	public static final int[] dx = { 1, 0, -1, 0 };
	public static final int IMPOSSIBLE = -1;

	public static int height, width, K;

	public static int[][] grid = new int[MAX_HEIGHT + 1][MAX_WIDTH + 1];
	public static boolean[][][] isVisited = new boolean[MAX_HEIGHT + 1][MAX_WIDTH + 1][MAX_K + 1];
	
	public static class Position {
		int y;
		int x;
		int brokeWall;
		
		public Position(int y, int x, int brokeWall) {
			super();
			this.y = y;
			this.x = x;
			this.brokeWall = brokeWall;
		}
		
		public boolean isInRange() {
			if (1 <= this.y && this.y <= height && 1 <= this.x && this.x <= width) {
				return true;
			}

			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 그리드 입력
		for (int y = 1; y <= height; y++) {
			String line = br.readLine();
			for (int x = 1; x <= width; x++) {
				grid[y][x] = line.charAt(x - 1) - '0';
			}
		}
		br.close();

		int answer = bfs(new Position(1, 1, 0));
		System.out.println(answer);
	}

	/** bfs */
	public static int bfs(Position start) {
		Queue<Position> q = new LinkedList<>();
		isVisited[start.y][start.x][0] = true; 
		q.offer(start);
		int count = 1;
		
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				// 다 왔으면 종료
				if (here.y == height && here.x == width) {
					return count;
				}
				
				// 사방 탐색 시도
				for (int dir = 0; dir < DIRECTIONS; dir++) {
					// 다음 위치
					Position there = new Position(here.y + dy[dir], here.x + dx[dir], here.brokeWall);
					if (there.isInRange() && !isVisited[there.y][there.x][there.brokeWall]) {
						// 벽이 아니라서 그냥 가도 되는 경우
						if (grid[there.y][there.x] == 0) {
							q.offer(there);
							isVisited[there.y][there.x][there.brokeWall] = true; 
						}
						
						// 벽이고 부술 수 있는 횟수가 남은 경우, 부숴본 상태를 큐에 넣어본다.
						else {
							if (there.brokeWall < K) {
								q.offer(new Position(there.y, there.x, there.brokeWall + 1));
								isVisited[there.y][there.x][there.brokeWall] = true;
							}
						}
					}
				}
			}
			
			count++;
		}
		
		return IMPOSSIBLE;
	}
}