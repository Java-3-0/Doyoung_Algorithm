// 39004KB, 272ms

package bj17135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언

	/** 그리드 높이 최대치, 그리드 너비 최대치, 궁수 사거리 최대치 */
	static final int MAX_H = 15, MAX_W = 15, MAX_D = 10;
	/** 그리드 칸의 상태를 나타내는 상수 */
	static final int EMPTY = 0, ENEMY = 1;
	/** 궁수 수 */
	static final int NUM_ARCHERS = 3;
	/** 3방탐색 방향의 y 변화량 */
	static final int[] dy = { 0, -1, 0 };
	/** 3방탐색 방향의 x 변화량 */
	static final int[] dx = { -1, 0, 1 };

	// 전역변수 선언

	/** 그리드 높이, 그리드 너비, 궁수 사거리 */
	static int H, W, D;
	/** 그리드 초기 상태 */
	static int[][] grid;
	/** 그리드를 복사해서 사용하기 위한 배열 */
	static int[][] gridCopy;
	/** 방문 여부 */
	static boolean[][] isVisited;
	/** 궁수 x좌표의 조합 */
	static int[] combi = new int[NUM_ARCHERS];
	/** 초기 그리드 상태에서 적의 수를 카운트할 변수 */
	static int cntEnemies = 0;
	/** 정답(최대 킬 수)을 담을 변수 */
	static int answer = 0;

	// 객체 선언

	/** 위치 객체 */
	static class Position {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;

		/** y좌표, x좌표를 입력받아 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 위치가 그리드 범위 내인지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
				return true;
			}

			return false;
		}

		/** 위치의 방문 여부를 리턴 */
		public boolean isVisited() {
			return isVisited[this.y][this.x];
		}

		/** 위치를 방문해서 방문 여부를 true로 갱신 */
		public void visit() {
			isVisited[this.y][this.x] = true;
		}

		/** 위치에서 dir 방향으로 한 칸 떨어진 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		/** 위치 객체를 HashSet에 사용하기 위한 hashCode 함수 */
		@Override
		public int hashCode() {
			return MAX_W * this.y + this.x;
		}

		/** 위치 객체를 HashSet에 사용하기 위한 equals 함수 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position pos = (Position) obj;
				if (this.y == pos.y && this.x == pos.x) {
					return true;
				}
				return false;
			}

			return super.equals(obj);
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// H, W, D 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		grid = new int[H + 1][W];
		gridCopy = new int[H + 1][W];
		isVisited = new boolean[H + 1][W];

		// grid 입력받으면서 적의 수도 센다
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				int input = Integer.parseInt(st.nextToken());
				grid[y][x] = input;
				if (input == ENEMY) {
					cntEnemies++;
				}
			}
		}

		// 모든 조합 완전탐색하면서 answer 갱신
		combination(0, 0);

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** 궁수를 배치하는 모든 조합을 완전탐색하면서 answer 생신 */
	public static void combination(int cnt, int startNum) {
		// 조합이 완성된 경우
		if (cnt == 3) {
			// 시뮬레이션 수행
			int kills = countKills();

			// 최대 킬 갱신
			answer = answer < kills ? kills : answer;
			return;
		}

		// 조합을 완성하지 못하고 idx 끝까지 간 경우
		if (startNum == W) {
			return;
		}

		// 조합을 만들어보는 경우
		for (int num = startNum; num < W; num++) {
			combi[cnt] = num;
			combination(cnt + 1, num + 1);
		}
	}

	/** 현재 combi[]에 궁수를 배치했을 때 게임이 끝날 때까지 킬 수를 구해서 리턴 */
	public static int countKills() {
		// grid의 초기 상태를 gridCopy에 복사
		initGridCopy();

		// 제거 수를 셀 변수
		int kills = 0, arrives = 0;

		// 게임이 종료될 때까지 반복
		while (kills + arrives < cntEnemies) {
			// 궁수가 사격
			kills += shootAndReturnKillCount();

			// 살아남은 적들이 이동
			arrives += moveAndReturnOutCount();
		}

		return kills;
	}

	/** 궁수들이 사거리 내의 가장 가까운 적을 사격하는 것을 시뮬레이션한 후 죽인 적 수를 리턴 */
	public static int shootAndReturnKillCount() {
		// 죽일 적들을 담을 Set
		Set<Position> enemiesDied = new HashSet<>();

		// 각 궁수 위치에서 가장 가까운 적을 찾아서 enemiesDied에 담는다
		for (int x : combi) {
			Position die = getClosestEnemy(x);

			if (die != null) {
				enemiesDied.add(die);
			}
		}

		// enemiesDied에 담긴 적들을 죽인다
		for (Position pos : enemiesDied) {
			gridCopy[pos.y][pos.x] = EMPTY;
		}

		return enemiesDied.size();
	}

	/** 살아남은 적들이 한 칸 아래로 이동하는 것을 시뮬레이션하고, 맨 아래 줄에 도달해서 제거되는 적들의 수를 리턴 */
	public static int moveAndReturnOutCount() {
		// 아래로 한 칸씩 이동
		for (int y = H; y >= 1; y--) {
			for (int x = 0; x < W; x++) {
				gridCopy[y][x] = gridCopy[y - 1][x];
			}
		}

		// 맨 윗 줄은 EMPTY 상태로
		Arrays.fill(gridCopy[0], EMPTY);

		// 맨 아래 줄에 도달한 적들의 개수를 세서 리턴
		int ret = 0;
		for (int x = 0; x < W; x++) {
			if (gridCopy[H][x] == ENEMY) {
				ret++;
			}
		}

		return ret;
	}

	/** 위치 p로부터 사거리 내의 가장 가까운 적 위치를 리턴, 사거리 내에 적이 없으면 null을 리턴 */
	public static Position getClosestEnemy(int x) {
		// 방문 여부 초기화
		initIsVisited();

		// bfs를 위한 큐 메모리 할당
		Queue<Position> q = new LinkedList<>();

		// 초기 위치 처리
		Position start = new Position(H - 1, x);
		start.visit();
		q.offer(start);

		// 사거리 내에서 bfs 수행
		int depth = 1;
		while (!q.isEmpty() && depth <= D) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				// 큐에서 위치를 하나 뽑아온다.
				Position now = q.poll();

				// 이 위치에서 적을 만나면 리턴
				if (gridCopy[now.y][now.x] == ENEMY) {
					return now;
				}

				// 인접한 위치들 중 방문하지 않은 위치들을 큐에 넣는다
				for (int dir = 0; dir < dy.length; dir++) {
					Position next = now.getNextPos(dir);
					if (next.isInRange() && !next.isVisited()) {
						q.offer(next);
					}
				}

			} // end for i

			depth++;

		} // end while

		return null;
	}

	/** isVisited[][]를 false로 초기화 */
	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}

	/** gridCopy[][]를 grid[][]에 있는 값대로 초기화 */
	public static void initGridCopy() {
		for (int y = 0; y < H + 1; y++) {
			for (int x = 0; x < W; x++) {
				gridCopy[y][x] = grid[y][x];
			}
		}
	}

}