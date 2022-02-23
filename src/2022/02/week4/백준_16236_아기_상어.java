//12752KB, 108ms

package bj16236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언
	/** 그리드 최대 크기 */
	static final int MAX_N = 20;
	/** 상어의 최초 크기 */
	static final int SHARK_START_SIZE = 2;
	/** 탐색할 방향 개수 */
	static final int DIRECTIONS = 4;
	/** 상, 좌, 우, 하 순서로 탐색하기 위한 y의 위치 변화량 */
	static final int[] dy = { -1, 0, 0, 1 };
	/** 상, 좌, 우, 하 순서로 탐색하기 위한 x의 위치 변화량 */
	static final int[] dx = { 0, -1, 1, 0 };

	// 전역변수 선언
	/** 그리드 크기 */
	static int N;
	/** 그리드 */
	static Grid grid;
	/** 그리드 각 칸의 방문 여부 */
	static boolean[][] isVisited = new boolean[MAX_N][MAX_N];
	/** 상어 */
	static Shark shark;

	// 객체 선언
	/** 그리드를 나타내는 객체 */
	static class Grid {
		int[][] arr;

		public Grid(int[][] arr) {
			super();
			this.arr = arr;
		}

		/** 위치 객체를 받아서 그 위치에 해당하는 공간에 해당하는 상태를 리턴 */
		public int get(Position p) {
			return this.arr[p.y][p.x];
		}

		/** 위치 객체와 갱신할 값을 받아서 그 위치를 갱신 */
		public void set(Position p, int val) {
			this.arr[p.y][p.x] = val;
		}
	}

	/** 위치를 나타내는 객체 */
	static class Position implements Comparable<Position>{
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;

		/** y좌표, x좌표를 받아서 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		/** 위치가 그리드 범위 안에 들어가는지 여부를 리턴 */
		public boolean isInRange() {
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
				return true;
			}

			return false;
		}

		/** 위치가 물고기인지 여부를 리턴 */
		public boolean isFish() {
			if (isInRange() && 1 <= grid.get(this) && grid.get(this) <= 6) {
				return true;
			} else {
				return false;
			}
		}

		/** dir 방향으로 한 칸 거리에 있는 위치를 리턴 */
		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		/** 비교기준 1순위: 위에 있을 수록, 비교기준 2순위: 왼쪽에 있을 수록, 앞에 오도록 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Position p) {
			if (this.y == p.y) {
				return this.x - p.x;
			}
			return this.y - p.y;
		}
	}

	/** 상어를 나타내는 객체 */
	static class Shark {
		/** 상어의 위치 */
		Position pos;
		/** 상어의 크기 */
		int size;
		/** 현재까지 먹은 물고기 수 */
		int eatCount;

		/** 위치를 파라미터로 받아서 초기 크기의 공복인 상어 객체를 생성하는 생성자 */
		public Shark(Position pos) {
			super();
			this.pos = pos;
			this.size = SHARK_START_SIZE;
			this.eatCount = 0;
		}

		/** 상어가 위치 p로 갈 수 있는지 여부를 리턴 */
		public boolean canGo(Position p) {
			if (p.isInRange() && grid.get(p) <= this.size) {
				return true;
			}

			return false;
		}

		/** 상어가 위치 p에 있는 것을 먹을 수 있는지 여부를 리턴 */
		public boolean canEat(Position p) {
			if (p.isFish() && grid.get(p) < this.size) {
				return true;
			}

			return false;
		}

		/** 상어가 위치 p에 존재하는 물고기를 먹는다 */
		public void eat(Position p) {
			// 먹은 누적 개수를 증가시킨다.
			this.eatCount++;
			
			// 먹은 누적 개수가 자신의 크기와 같다면, 크기를 1 증가시키고 누적 개수는 0으로 초기화한다.
			if (this.eatCount == this.size) {
				this.size++;
				this.eatCount = 0;
			}
			
			// 맵에서 먹힌 물고기를 삭제하고 빈 칸으로 만든다.
			grid.set(p, 0);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		N = Integer.parseInt(br.readLine());

		// 그리드 각 칸의 정보 입력
		int[][] input = new int[N][N];
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				int status = Integer.parseInt(st.nextToken());

				// 상어면 상어 객체만 만들어 두고, 맵에는 빈 칸을 넣는다.
				if (status == 9) {
					shark = new Shark(new Position(y, x));
					input[y][x] = 0;
				}
				// 상어가 아니면 그대로 맵에 넣는다.
				else {
					input[y][x] = status;
				}
			}
		}

		// 입력받은 맵 정보로 그리드 객체 생성
		grid = new Grid(input);

		// 물고기를 잡아 먹을 수 있는 시간 계산
		int answer = getMaxTime();
		
		// 출력
		System.out.println(answer);
		
	} // end main

	/** bfs를 반복적으로 수행하면서 물고기를 잡아먹을 수 있는 시간을 구해서 리턴 */
	public static int getMaxTime() {
		int ret = 0;

		int time = 0;
		// 한 마리 씩 먹는 것을 더 이상 먹을 물고기가 없을 때까지 반복한다.
		do {
			time = tryEatOne();
			ret += time;
		} while (time != 0);
		
		return ret;
	}

	/**
	 * bfs를 통해 상어가 가장 가까운 물고기 한 마리를 잡아먹으러 가는 것을 시도해서 걸린 시간을 리턴
	 * 
	 * @return 잡아먹기에 성공하면 걸린 시간을 리턴, 실패하면 0을 리턴
	 */
	public static int tryEatOne() {
		// isVisited 배열 초기화
		initIsVisited();

		// bfs를 위한 큐 생성
		Queue<Position> q = new LinkedList<>();

		// 시작 위치 방문하고 큐에 넣음
		Position startPos = shark.pos;
		visit(startPos);
		q.offer(startPos);

		// depth는 0부터 시작
		int depth = 0;
		
		// 먹을 수 있는 물고기들 중 가장 가까운 것들을 담을 우선순위 큐
		PriorityQueue<Position> closestEatablePQ = new PriorityQueue<>();
		
		// 더 이상 갈 수 있는 곳이 없어서 큐가 빌 때까지 반복
		while (!q.isEmpty()) {
			int size = q.size();
			// 한 depth에 있는 위치들에서 갈 수 있고 아직 방문하지 않은 위치들을 큐에 넣는다
			for (int i = 0; i < size; i++) {
				Position here = q.poll();

				// 먹을 수 있는 물고기를 찾았다면
				if (shark.canEat(here)) {
					closestEatablePQ.offer(here);
				}

				// 다음으로 갈 수 있는 위치들을 큐에 넣는다.
				for (int dir = 0; dir < DIRECTIONS; dir++) {
					Position there = here.getNextPos(dir);
					if (shark.canGo(there) && !isVisited(there)) {
						visit(there);
						q.offer(there);
					}
				} // end for (dir)
			} // end for (i)

			// 1마리 이상 찾았다면 그 중 가장 앞의 것을 먹는다.
			if (closestEatablePQ.size() != 0) {
				Position posToEat = closestEatablePQ.peek();
				// 먹는다
				shark.eat(posToEat);
				// 이 위치부터 새로 bfs를 시도해야 하니 상어 위치를 갱신한다.
				shark.pos = posToEat;
				// depth가 곧 걸린 시간이니 depth를 리턴
				return depth;
			}
			
			// 큐 사이즈만큼 수행하는 루프가 한 번 끝나고 나면, 다음 루프는 다음 depth니까 depth 값을 갱신한다.
			depth++;

		} // end while

		// bfs를 끝까지 수행했는데 먹을 수 있는 물고기가 없었다면 0을 리턴
		return 0;
	}

	/** p위치를 방문했는지 여부를 리턴 */
	public static boolean isVisited(Position p) {
		return isVisited[p.y][p.x];
	}

	/** p위치를 방문하여 isVisited를 갱신 */
	public static void visit(Position p) {
		isVisited[p.y][p.x] = true;
	}

	/** isVisited 2차원 배열을 false로 초기화 */
	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
}
