// 23864KB, 408ms

package bj15683;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언

	/** 최대 사무실 크기 */
	static final int MAX_SIZE = 8;
	/** 최대 CCTV 개수 */
	static final int MAX_CCTV = 8;
	/** 방향 개수 */
	static final int DIRECTIONS = 4;
	/** 각 방향을 나타내는 상수 */
	static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	/** 벽을 나타내는 상수 */
	static final int WALL = 6;
	/** 각 방향으로 갈 때의 y, x 변화량 */
	static final int[] dy = { -1, 0, 1, 0 };
	static final int[] dx = { 0, 1, 0, -1 };
	/** 방향 -> 그 방향의 시계방향으로의 매핑 */
	static final Map<Integer, Integer> clockwise = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 9084348478485385196L;
		{
			put(UP, RIGHT);
			put(RIGHT, DOWN);
			put(DOWN, LEFT);
			put(LEFT, UP);
		}
	};

	// 전역변수 선언
	
	/** 사무실의 세로방향 크기 */
	static int height;
	/** 사무실의 가로방향 크기 */
	static int width;
	/** CCTV 카메라 객체들을 담아둘 리스트 */
	static List<Camera> cameras = new ArrayList<Camera>();
	/** 카메라 개수 */
	static int numCameras;
	/** 입력받은 사무실 상태를 저장해둘 2차원 배열 */
	static int[][] office = new int[MAX_SIZE][MAX_SIZE];
	/** 사무실의 각 위치마다의 감시 여부를 기록할 2차원 배열 */
	static boolean[][] isWatched = new boolean[MAX_SIZE][MAX_SIZE];
	/** 각 카메라의 방향에 대한 중복순열 */
	static int[] permu;
	/** 사각지대의 최소 개수로, 구해야 하는 정답 */
	static int answer = Integer.MAX_VALUE;
	
	// 객체 선언

	/** CCTV 카메라를 나타내는 객체 */
	static class Camera {
		/** y좌표 */
		int y;
		/** x좌표 */
		int x;
		/** 카메라의 종류 */
		int type;
		
		/** y좌표, x좌표, 카메라 종류를 입력받아 카메라 객체를 생성하는  생성자 */
		public Camera(int y, int x, int num) {
			super();
			this.y = y;
			this.x = x;
			this.type = num;
		}
	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		for (int y = 0; y < height; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < width; x++) {
				int num = Integer.parseInt(st.nextToken());
				// 오피스 상태 저장
				office[y][x] = num;
				// 카메라라면 카메라 객체를 만들어서 cameras 리스트에 추가
				if (1 <= num && num <= 5) {
					cameras.add(new Camera(y, x, num));
				}
			}
		}
		// 카메라 개수 파악
		numCameras = cameras.size();
		// 순열의 크기는 카메라 개수와 같게 설정
		permu = new int[numCameras];
		// 모든 카메라의 모든 방향에 대해 완전탐색하면서 최소 사각지대 개수를 찾는다
		permutation(0);
		// 정답 출력
		System.out.println(answer);
	}
	
	/** 카메라 방향에 대한 모든 중복순열을 만들어 보며, 사각지대 개수 중 최소값을 갱신 */
	public static void permutation(int cnt) {
		// 중복순열이 다 만들어진 경우, 이 permu라는 카메라 방향들로 사각지대 개수를 세어본다
		if (cnt == cameras.size()) {
			int count = countBlindSpots();
			if (count < answer) {
				answer = count;
			}
			
			return;
		}

		// 중복순열을 만든다
		for (int pick = 0; pick < DIRECTIONS; pick++) {
			permu[cnt] = pick;
			permutation(cnt + 1);
		}
	}
	
	/** 현재 permu에서의 사각지대 개수를 리턴 */
	public static int countBlindSpots() {
		initIsWatched();
		
		// 카메라마다 감시해 본다
		for (int i = 0; i < numCameras; i++) {
			Camera cam = cameras.get(i);
			int mainDirection = permu[i];
			
			int startY = cam.y;
			int startX = cam.x;
			int type = cam.type;
			
			switch (type) {
			// 메인 방향만 감시
			case 1:
				watch(startY, startX, mainDirection);
				break;
			// 메인 방향, 180도 방향을 감시
			case 2:
				watch(startY, startX, mainDirection);
				watch(startY, startX, clockwise.get(clockwise.get(mainDirection)));
				break;
			// 메인 방향, 90도 방향을 감시
			case 3:
				watch(startY, startX, mainDirection);
				watch(startY, startX, clockwise.get(mainDirection));
				break;
			// 메인 방향, 90도 방향, 180도 방향을 감시
			case 4:
				watch(startY, startX, mainDirection);
				watch(startY, startX, clockwise.get(mainDirection));
				watch(startY, startX, clockwise.get(clockwise.get(mainDirection)));
				break;
			// 메인 방향, 90도 방향, 180도 방향, 270도 방향을 감시
			case 5:
				watch(startY, startX, mainDirection);
				watch(startY, startX, clockwise.get(mainDirection));
				watch(startY, startX, clockwise.get(clockwise.get(mainDirection)));
				watch(startY, startX, clockwise.get(clockwise.get(clockwise.get(mainDirection))));
				break;
			default:
				break;
			}
		}
		
		// 사각지대 개수 세기
		int ret = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!isWatched[y][x] && office[y][x] != WALL) {
					ret++;
				}
			}
		}
		
		// 사각지대 개수 리턴
		return ret;
	}
	

	/** isWatched 2차원 배열을 false로 초기화 */
	public static void initIsWatched() {
		for (int i = 0; i < isWatched.length; i++) {
			Arrays.fill(isWatched[i], false);
		}
	}
	
	/** (y, x)로부터 dir 방향을 감시한다. */
	public static void watch (int startY, int startX, int dir) {
		// 벽을 만나거나 오피스 범위를 벗어나기 전까지 감시
		for (int y = startY, x = startX; isInRange(y, x) && office[y][x] != WALL; y += dy[dir], x += dx[dir]) {
			isWatched[y][x] = true;
		}
	}
	
	/** (y, x)가 사무실 범위 내에 들어가면 true, 아니면 false를 리턴한다. */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < height && 0 <= x && x < width) {
			return true;
		}
		
		return false;
	}
}
