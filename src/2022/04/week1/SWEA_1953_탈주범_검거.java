// 25676KB, 189ms

package swea1953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	// 상수 선언
	
	/** H, W, time의 최대값 */
	static final int MAX_H = 50, MAX_W = 50, MAX_TIME = 50;
	/** 사방탐색 y 변화량 */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 */
	static final int[] dx = { 0, 0, -1, 1 };
	/** 각 방향을 나타내는 상수로, dy[], dx[]에서 인덱스로 사용할 수 있다 */
	static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	
	// 전역변수 선언
	
	/** 그리드 높이, 너비, 시작 위치의 y좌표, 시작 위치의 x좌표, 탈주범의 최대 이동 가능 시간 */
	static int H, W, startY, startX, time;
	/** 그리드 */
	static int[][] grid = new int[MAX_H][MAX_W];
	/** 방문 여부 */
	static boolean[][] isVisited = new boolean[MAX_H][MAX_W];
	/** 파이프 타입 -> 그 파이프가 뚫려 있는 방향들로의 매핑 */
	static Map<Integer, List<Integer>> pipeToDir = new HashMap<>();

	// 객체 선언
	
	/** 위치 객체 */
	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 현재 위치에서 dir 방향으로 한 칸 이동한 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		/** 현재 위치에서 파이프가 dir 방향으로 연결되어 있는지 여부를 리턴 */
		public boolean isConnected(int dir) {
			Position next = this.getNextPos(dir);
			List<Integer> nextDirections = pipeToDir.get(next.getGridVal());
			
			if (nextDirections.contains(getOppositeDir(dir))) {
				return true;
			}
			
			return false;
		}

		/** 현재 위치가 grid 범위 내인지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}

			return false;
		}
		
		/** 현재 위치의 방문 여부 리턴 */
		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}
		
		/** 현재 위치의 방문 여부를 true로 갱신 */
		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		/** 그리드에서 현재 위치에 해당하는 값을 리턴 */
		public int getGridVal() {
			return grid[this.y][this.x];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// (파이프 타입 -> 이동 가능한 방향들의 리스트)의 매핑을 pipeToDir에 담는다.
		makePipeMappings();

		// 테스트케이스 수만큼 수행
		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// isVisited 배열 초기화
			initIsVisited();
			
			// H, W, startY, startX, time 입력
			st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			startY = Integer.parseInt(st.nextToken());
			startX = Integer.parseInt(st.nextToken());
			time = Integer.parseInt(st.nextToken());

			// grid 입력
			for (int y = 0; y < H; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < W; x++) {
					grid[y][x] = Integer.parseInt(st.nextToken());
				}
			}

			// time 시간 내에 방문 가능한 정점 수를 bfs로 계산
			int answer = bfs();

			// 정답을 형식에 맞게 출력 스트링빌더에 추가
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** isVisited 배열 초기화 */
	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
	
	/** bfs를 수행하며 time 내에 도달 가능한 정점 수를 카운트해서 리턴 */
	public static int bfs() {
		Queue<Position> q = new LinkedList<>();

		// 초기 위치 처리
		Position start = new Position(startY, startX);
		start.visit();
		q.offer(start);

		// depth <= time까지 bfs 수행하면서 방문 정점 수 계산
		int depth = 1;
		int cnt = 0;
		while (!q.isEmpty() && depth <= time) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				// 큐에서 하나를 빼서 현재 위치로 하고 카운트 갱신
				Position now = q.poll();
				cnt++;

				// 이 위치의 파이프가 통하는 방향들에 대해 수행
				int pipeType = now.getGridVal();
				List<Integer> directions = pipeToDir.get(pipeType);
				for (int dir : directions) {
					// 다음 위치 계산
					Position next = now.getNextPos(dir);
					
					// 이 다음 위치가 그리드 범위 내이고, 아직 방문하지 않았고, 파이프로 연결된 위치라면 큐에 넣는다.
					if (next.isInRange() && !next.isVisited() && now.isConnected(dir)) {
						next.visit();
						q.offer(next);
					}
				}

			}
			depth++;
		}

		return cnt;
	}

	/** dir 방향의 역방향을 리턴 */
	public static int getOppositeDir(int dir) {
		if (dir == UP) {
			return DOWN;
		} else if (dir == DOWN) {
			return UP;
		} else if (dir == LEFT) {
			return RIGHT;
		} else if (dir == RIGHT){
			return LEFT;
		} else {
			return -1;
		}
	}

	/** 각 파이프 타입에서 이동할 수 있는 방향을 pipeToDir 이라는 맵에 담는다 */
	public static void makePipeMappings() {
		pipeToDir.put(0, new ArrayList<Integer>());
		pipeToDir.put(1, new ArrayList<Integer>(Arrays.asList(UP, DOWN, LEFT, RIGHT)));
		pipeToDir.put(2, new ArrayList<Integer>(Arrays.asList(UP, DOWN)));
		pipeToDir.put(3, new ArrayList<Integer>(Arrays.asList(LEFT, RIGHT)));
		pipeToDir.put(4, new ArrayList<Integer>(Arrays.asList(UP, RIGHT)));
		pipeToDir.put(5, new ArrayList<Integer>(Arrays.asList(DOWN, RIGHT)));
		pipeToDir.put(6, new ArrayList<Integer>(Arrays.asList(DOWN, LEFT)));
		pipeToDir.put(7, new ArrayList<Integer>(Arrays.asList(UP, LEFT)));
	}

}
