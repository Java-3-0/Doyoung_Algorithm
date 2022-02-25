// 23660KB, 248ms

package bj17144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언
	/** 그리드 최대 높이, 최대 너비 */
	static final int MAX_HEIGHT = 50, MAX_WIDTH = 50;
	/** 최대 시간 (초) */
	static final int MAX_TIME = 1000;
	/** 각 방향을 나타내는 상수 */
	static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	/** 사방탐색 y 변화량 (방향 상수 = 인덱스) */
	static final int[] dy = { -1, 1, 0, 0 };
	/** 사방탐색 x 변화량 (방향 상수 = 인덱스) */
	static final int[] dx = { 0, 0, -1, 1 };
	/** (방향 -> 그 방향의 시계방향)으로의 매핑 */
	static final Map<Integer, Integer> clockwise = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 8956946061062224255L;
		{
			put(UP, RIGHT);
			put(RIGHT, DOWN);
			put(DOWN, LEFT);
			put(LEFT, UP);
		}
	};
	/** (방향 -> 그 방향의 반시계방향)으로의 매핑 */
	static final Map<Integer, Integer> counterClockwise = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = -4971573934318357314L;
		{
			put(UP, LEFT);
			put(LEFT, DOWN);
			put(DOWN, RIGHT);
			put(RIGHT, UP);
		}
	};

	// 전역변수 선언
	/** 그리드 높이, 그리드 너비, 시뮬레이션 수행 시간 */
	static int height, width, time;
	/** 방 상태를 나타내는 그리드 */
	static int[][] grid;
	/** 공기청정기의 y 좌표들 */
	static int[] airCleanerY = new int[2];
	/** 공기청정기의 x 좌표 */
	static int airCleanerX = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 높이, 그리드 너비, 시뮬레이션 수행 시간 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		time = Integer.parseInt(st.nextToken());

		// 그리드 각 칸의 정보 입력
		grid = new int[height][width];
		int cnt = 0;
		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == -1) {
					grid[y][x] = 0; // 공기청정기가 있는 칸의 미세먼지 수는 0으로
					airCleanerY[cnt++] = y; // 공기청정기의 y좌표를 따로 저장
				} else {
					grid[y][x] = input; // 나머지 칸은 입력받은 그대로
				}
			}
		}

		// t초 동안 미세먼지 확산, 에어컨 바람 순환 반복
		for (int t = 0; t < time; t++) {
			dustDiffuse();
			airCirculate();
		}

		// 최종 상태의 먼지 개수 계산
		int answer = getDustCount();
		
		// 출력
		System.out.println(answer);

	} // end main

	
	
	/** 1회의 미세먼지 확산이 끝나고 난 후의 상태로 grid를 갱신 */
	public static void dustDiffuse() {
		// 확산이 동시에 일어나야 하니 grid 자체는 건드리지 않고 과정을 result에 계산한 뒤 마지막에 grid에 덮어씌우기 위해 만든 임시 배열
		int[][] result = new int[height][width];

		// 일단 현재 상태 그대로를 복사
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				result[y][x] = grid[y][x];
			}
		}

		// 각 칸마다 미세먼지 확산
		for (int fromY = 0; fromY < height; fromY++) {
			for (int fromX = 0; fromX < width; fromX++) {
				for (int dir = 0; dir < dy.length; dir++) {
					int toY = fromY + dy[dir];
					int toX = fromX + dx[dir];

					// 확산될 칸이 맵 범위 내라면
					if (isInRange(toY, toX)) {
						// 공기청정기가 있는 칸으로는 확산되지 않는다.
						if (toX == airCleanerX && (toY == airCleanerY[0] || toY == airCleanerY[1])) {
							continue;
						}
						// 이외의 칸으로는 확산된다.
						int diffuseAmount = grid[fromY][fromX] / 5;
						result[toY][toX] += diffuseAmount;
						result[fromY][fromX] -= diffuseAmount;
					}
				}

			}
		}

		grid = result;
	}

	/** 1회의 에어컨 바람 순환이 끝나고 난 후의 상태로 grid를 갱신 */
	public static void airCirculate() {
		airCirculateTop();
		airCirculateBottom();
	}

	/** 공기청정기의 위쪽 부분 공기 순환을 처리 */
	public static void airCirculateTop() {
		// 공기청정기의 위치
		int startY = airCleanerY[0];
		int startX = airCleanerX;

		// 시작 위치를 공기청정기의 위치로 초기화
		int y = startY;
		int x = startX;

		// 시작 방향은 위쪽으로 초기화
		int dir = UP;

		// 시계방향으로 한 바퀴 돌면서 미세먼지를 한 칸씩 당겨온다
		for (int i = 0; i < 4; i++) {
			int len = (i % 2 == 0) ? startY : width - 1;

			for (int k = 0; k < len; k++) {
				int nextY = y + dy[dir];
				int nextX = x + dx[dir];
				grid[y][x] = grid[nextY][nextX];
				y = nextY;
				x = nextX;
			}

			dir = clockwise.get(dir);
		}

		// 공기청정기의 위치와 그 옆 위치는 미세먼지를 없앤다.
		grid[startY][startX] = 0;
		grid[startY][startX + 1] = 0;
	}

	/** 공기청정기의 아래쪽 부분 공기 순환을 처리 */
	public static void airCirculateBottom() {
		// 공기청정기의 위치
		int startY = airCleanerY[1];
		int startX = airCleanerX;

		// 시작 위치를 공기청정기의 위치로 초기화
		int y = startY;
		int x = startX;

		// 시작 방향은 아래쪽으로 초기화
		int dir = DOWN;

		// 반시계방향으로 한 바퀴 돌면서 미세먼지를 한 칸씩 당겨온다
		for (int i = 0; i < 4; i++) {
			int len = (i % 2 == 0) ? height - startY - 1 : width - 1;

			for (int k = 0; k < len; k++) {
				int nextY = y + dy[dir];
				int nextX = x + dx[dir];
				grid[y][x] = grid[nextY][nextX];
				y = nextY;
				x = nextX;
			}

			dir = counterClockwise.get(dir);
		}

		// 공기청정기의 위치와 그 옆 위치는 미세먼지를 없앤다.
		grid[startY][startX] = 0;
		grid[startY][startX + 1] = 0;
	}

	/** 방에 존재하는 먼지의 개수를 리턴 */
	public static int getDustCount() {
		int ret = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ret += grid[y][x];
			}
		}

		return ret;
	}

	/** (y, x)가 그리드 범위 내에 들어가는지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < height && 0 <= x && x < width) {
			return true;
		}
		return false;
	}
}