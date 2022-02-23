package bj7569;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_SIZE = 100;
	static final int[] dz = {0, 0, 0, 0, -1, 1};
	static final int[] dy = {0, 0, -1, 1, 0, 0};
	static final int[] dx = {-1, 1, 0, 0, 0, 0};
	
	static int M, N, H;
	static int[][][] grid = new int[MAX_SIZE][MAX_SIZE][MAX_SIZE];
	
	static class Position {
		/** z좌표 */
		int z;
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;
		
		/** z좌표, y좌표, x좌표를 파라미터로 받아 위치 객체를 생성하는 생성자 */
		public Position(int z, int y, int x) {
			super();
			this.z = z;
			this.y = y;
			this.x = x;
		}
		
		/** 위치가 그리드 범위 내인지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.z && this.z < H && 0 <= this.y && this.y < N && 0 <= this.x && this.x < M) {
				return true;
			}
			return false;
		}
		
		/** 위치에서 dir 방향으로 한 칸 간 다음 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.z + dz[dir], this.y + dy[dir], this.x + dx[dir]);
		}
	}
	
	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 너비, 높이 입력
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		// 그리드 각 칸의 상태 입력
		for (int z = 0; z < H; z++) {
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < M; x++) {
					grid[z][y][x] = Integer.parseInt(st.nextToken());
				}
			}
		}
		
		// 토마토가 모두 익는 최소 일수 계산
		int answer = getMinDays();
		
		// 정답 출력
		System.out.println(answer);
		
	} // end main
	
	/** 토마토가 모두 익는 데 걸리는 시간을 리턴 */
	public static int getMinDays() {
		Queue<Position> q = new LinkedList<>();
		int tomatoCount = 0;
		int targetCount = 0;
		
		// 시작부터 익어있는 토마토들을 큐에 넣는다.
		for (int z = 0; z < H; z++) {
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					if (grid[z][y][x] == 1) {
						q.offer(new Position(z, y, x));
					}
					else if (grid[z][y][x] == 0) {
						targetCount++;
					}
					
				} // end for x
			} // end for y
		} // end for z

		
		// 시작부터 토마토가 다 익어있는 상황 처리
		if (tomatoCount == targetCount) {
			return 0;
		}
		
		// 이외의 경우 bfs로 익혀 본다.
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Position here = q.poll();
				for (int dir = 0; dir < dy.length; dir++) {
					Position there = here.getNextPos(dir);
					if (there.isInRange() && grid[there.z][there.y][there.x] == 0) {
						grid[there.z][there.y][there.x] = 1;
						tomatoCount++;
						q.offer(there);
					}
				}
				
			} // end for i	
			
			depth++;
		} // end while
		
		// 익혀야 하는 토마토 개수만큼 다 익혔으면 성공
		if (tomatoCount == targetCount) {
			return depth - 1;
		}
		
		// 아니면 실패
		else {
			return -1;
		}
	}
}