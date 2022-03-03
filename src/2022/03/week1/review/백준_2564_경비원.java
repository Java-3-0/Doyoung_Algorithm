// 11588KB, 76ms

package bj2564;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Position {
		int dir;
		int dist;

		public Position(int dir, int dist) {
			super();
			this.dir = dir;
			this.dist = dist;
		}

		/** 이 포지션과 파라미터로 받은 포지션의 거리를 계산해서 리턴 */
		public int getDistanceTo(Position p) {
			int dist = Math.abs(this.clockwiseFromOrigin() - p.clockwiseFromOrigin());
			
			return Math.min(dist, 2 * height + 2 * width - dist);
		}
		
		/** (0, 0)으로부터의 시계방향으로 갈 때의 거리를 리턴 */
		public int clockwiseFromOrigin() {
			switch (this.dir) {
			case NORTH:
				return this.dist;
			case EAST:
				return width + this.dist;
			case SOUTH:
				return width + height + (width - this.dist);
			case WEST:
				return width + height + width + (height - this.dist);
			}
			
			
			return 0;
		}
	}

	/** 사방위을 나타낼 상수 */
	public static final int NORTH = 1, SOUTH = 2, WEST = 3, EAST = 4;

	public static int height, width;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 블록 가로세로 길이 입력
		st = new StringTokenizer(br.readLine(), " ");
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());

		// 상점 정보 입력
		int numShops = Integer.parseInt(br.readLine());
		Position[] shops = new Position[numShops];
		for (int i = 0; i < numShops; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int dir = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			shops[i] = new Position(dir, dist);
		}

		// 동근이의 위치 입력
		st = new StringTokenizer(br.readLine(), " ");
		int targetDir = Integer.parseInt(st.nextToken());
		int targetDist = Integer.parseInt(st.nextToken());
		Position dongeun = new Position(targetDir, targetDist);

		// 동근이와 상점 사이의 거리를 구해서 합계에 누적
		int sum = 0;
		for (Position shop : shops) {
			sum += dongeun.getDistanceTo(shop);
		}

		// 합계 출력
		System.out.println(sum);
	}

}
