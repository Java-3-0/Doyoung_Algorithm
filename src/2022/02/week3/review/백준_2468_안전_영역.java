// 130576KB, 316ms

package bj2468;

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

	/** y좌표, x좌표를 입력받아 위치 객체를 생성하는 생성자 */
	public Position(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	/**
	 * 위치가 map 범위 안인지 여부를 리턴
	 * 
	 * @return 범위 안이면 true, 아니면 false
	 */
	public boolean isInRange() {
		if (0 <= this.y && this.y < Main.N && 0 <= this.x && this.x < Main.N) {
			return true;
		}
		return false;
	}

	/**
	 * 위치가 안전한지 여부를 리턴
	 * 
	 * @return 안전하면 true, 물에 잠기면 false
	 */
	public boolean isSafe(int rain) {
		if (isInRange() && Main.map[this.y][this.x] > rain) {
			return true;
		}
		return false;
	}
}

public class Main {
	public static final int MAX_N = 100;
	public static int[][] map = new int[MAX_N][MAX_N];
	public static boolean[][] isVisited = new boolean[MAX_N][MAX_N];
	public static int N;

	public static final int[] dy = { -1, 1, 0, 0 };
	public static final int[] dx = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();

		int maxAreas = 1;
		for (int rain = 1; rain <= 99; rain++) {
			int areas = countAreas(rain);
			if (maxAreas < areas) {
				maxAreas = areas;
			}
		}

		System.out.println(maxAreas);

	}

	public static void initIsVisited() {
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
	}
	
	/** 강수량이 주어졌을 때, 안전한 영역의 개수를 리턴 */
	public static int countAreas(int rain) {
		// isVisited 초기화
		initIsVisited();
		
		int count = 0;
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				Position start = new Position(y, x);
				// 새로운 안전 영역의 시작점을 만나면, 이 위치로부터 bfs 수행하면서 방문
				if (!isVisited[start.y][start.x] && start.isSafe(rain)) {
					bfs(start, rain);
					count++;
				}
			}
		}

		return count;
	}

	/** (y, x) 위치에서부터 인접한 안전 구역들을 방문 */
	public static void bfs(Position start, int rain) {
		// 시작 지점의 방문 여부 갱신
		isVisited[start.y][start.x] = true;
		
		// 큐에 시작 지점을 넣음
		Queue<Position> q = new LinkedList<>();
		q.offer(start);

		// 큐가 빌 때까지 수행
		while (!q.isEmpty()) {
			// 큐에서 현재 위치가 될 곳을 가져옴
			Position here = q.poll();

			// 인접 위치들 중 안전한 곳이 있다면 큐에 담음
			for (int dir = 0; dir < dy.length; dir++) {
				Position there = new Position(here.y + dy[dir], here.x + dx[dir]);
				
				if (there.isInRange() && !isVisited[there.y][there.x] && there.isSafe(rain)) {
					isVisited[there.y][there.x] = true;
					q.offer(there);
				}
			}
		}
	}
}
