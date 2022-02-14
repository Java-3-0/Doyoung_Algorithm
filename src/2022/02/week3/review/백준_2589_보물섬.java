// 293244KB, 488ms

package bj2589;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Position {
	int y;
	int x;

	/** y좌표와 x좌표를 입력받아 위치 객체를 생성하는 생성자 */
	public Position(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	/** 위치가 land인지 여부를 리턴 */
	public boolean isLand() {
		if (isInRange() && Main.map[this.y][this.x] == 'L') {
			return true;
		}
		return false;
	}

	/** 위치가 map 범위 안인지 여부를 리턴 */
	private boolean isInRange() {
		if (0 <= this.y && this.y < Main.height && 0 <= this.x && this.x < Main.width) {
			return true;
		}
		return false;
	}
}

public class Main {
	public static int height;
	public static int width;
	public static char[][] map;

	public static final int[] dy = { -1, 1, 0, 0 };
	public static final int[] dx = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		map = new char[height][width];

		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				map[y][x] = line.charAt(x);
			}
		}

		// bfs의 최대 깊이들 중 최대를 저장해 둘 변수
		int maxDist = 0;
		// 'L'이 쓰여진 각각의 칸을 시작점으로 하는 bfs의 최대 깊이를 구한다
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[y][x] == 'L') {
					int dist = getDepth(y, x);
					if (maxDist < dist) {
						maxDist = dist;
					}
				}
			}
		}

		// 출력
		System.out.println(maxDist);
	}

	/** (y, x) 위치에서 시작하는 bfs의 depth를 리턴 */
	public static int getDepth(int y, int x) {
		boolean[][] isVisited = new boolean[height][width];
		Position start = new Position(y, x);
		isVisited[start.y][start.x] = true; 
		Queue<Position> q = new LinkedList<>();
		q.offer(start);

		int depth = -1;
		while (!q.isEmpty()) {
			// 큐에 depth가 같은 것들끼리만 들어 있을 때 그 크기를 저장해 두고, 그 수만큼만 수행하면 같은 depth인 것들을 처리하게 됨
			int queueSize = q.size();
			for (int i = 0; i < queueSize; i++) {
				// 큐에서 현 위치를 가져옴
				Position here = q.poll();
				// 인접한 위치들 중 아직 방문하지 않은 land인 곳들을 큐에 담고, 방문 여부 갱신
				for (int dir = 0; dir < dy.length; dir++) {
					Position there = new Position(here.y + dy[dir], here.x + dx[dir]);
					if (there.isLand() && !isVisited[there.y][there.x]) {
						q.offer(there);
						isVisited[there.y][there.x] = true; 
					}
				}
			}
			// 한 depth를 다 처리했으니 depth 갱신
			depth++;
		}

		return depth;
	}
}
