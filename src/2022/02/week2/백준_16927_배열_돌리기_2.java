// 37032KB, 520ms

package baek16927;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	/** 각 방향을 상수로 선언 */
	public static final int DOWN = 0, RIGHT = 1, UP = 2, LEFT = 3;

	/** 각 이동 방향의 y 변화량 */
	public static final int[] dy = { 1, 0, -1, 0 };
	/** 각 이동 방향의 x 변화량 */
	public static final int[] dx = { 0, 1, 0, -1 };
	/** 방향 -> 이 방향에서의 반시계 방향으로의 매핑 */
	public static final Map<Integer, Integer> counterClockwise = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 5081870799884310337L;
		{
			put(DOWN, RIGHT);
			put(RIGHT, UP);
			put(UP, LEFT);
			put(LEFT, DOWN);
		}
	};

	/** 배열의 크기 */
	public static int height, width;
	/** 회전 횟수 */
	public static int spins;
	/** 돌릴 배열 */
	public static int[][] arr;

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		spins = Integer.parseInt(st.nextToken());

		arr = new int[height][width];
		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				arr[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 배열 회전
		spinLayer(0, 0, height, width);

		// 출력
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(arr[y][x]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	/**
	 * 2차원 배열의 가장 바깥쪽 레이어를 회전시키고, 안쪽 레이어에 대해 재귀 호출하는 것을 반복하여, 결과적으로 전체를 회전시킨다
	 *
	 * @param startY : 회전할 레이어의 왼쪽 상단 시작 지점의 y좌표
	 * @param startX : 회전할 레이어의 왼쪽 상단 시작 지점의 x좌표
	 * @param h      : 회전할 레이어의 높이
	 * @param w      : 회전할 레이어의 너비
	 */
	public static void spinLayer(int startY, int startX, int h, int w) {
		// base case : 최소 레이어까지 다 돌렸으면 리턴
		if (h < 2 || w < 2)
			return;

		// 시작 지점, 방향 설정
		int y = startY;
		int x = startX;
		int dir = DOWN;

		// 시계 반대 방향으로 돌면서 만나는 수들을 deque에 담음
		Deque<Integer> deque = new LinkedList<>();
		for (int i = 0; i < 4; i++) {
			// 담을 개수 계산
			int len = (i % 2 == 0) ? h : w;

			// deque에 담기
			for (int j = 1; j < len; j++) {
				deque.addLast(arr[y][x]);
				y += dy[dir];
				x += dx[dir];
			}

			// 방향 변경
			dir = counterClockwise.get(dir);
		}

		// 유효 회전 수 계산
		int netSpins = spins % deque.size();

		// 유효 회전수만큼 deque 뒤에서부터 앞으로 가져옴
		for (int i = 0; i < netSpins; i++) {
			deque.addFirst(deque.pollLast());
		}

		// 다시 시계 방향으로 돌면서 deque에 있는 값들을 하나씩 arr에 넣음
		for (int i = 0; i < 4; i++) {
			// 넣을 길이 계산
			int len = (i % 2 == 0) ? h : w;

			// arr에 넣기
			for (int j = 1; j < len; j++) {
				arr[y][x] = deque.pollFirst();
				y += dy[dir];
				x += dx[dir];
			}

			// 방향 변경
			dir = counterClockwise.get(dir);
		}

		// 한 겹 안쪽 레이어에 대해 재귀 호출
		spinLayer(startY + 1, startX + 1, h - 2, w - 2);

		return;
	}
}
