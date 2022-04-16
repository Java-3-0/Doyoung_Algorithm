// 31720KB, 856ms

package bj21611;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	/** 그리드 최대 크기 */
	static final int MAX_N = 49;
	/** 블리자드 시전 최대 횟수 */
	static final int MAX_M = 100;

	/** 그리드 크기 */
	static int N;
	/** 블리자드 시전 횟수 */
	static int M;
	/** 각 방향으로 일직선상에 있는 인덱스들의 리스트 */
	static List<Integer> left = new ArrayList<>(), right = new ArrayList<>(), up = new ArrayList<>(),
			down = new ArrayList<>();
	/** 남아있는 구슬들을 상어에서 가까운 순으로 담을 List */
	static List<Integer> marbles = new LinkedList<>();
	/** 각 번호 구슬의 폭발 횟수 */
	static int[] explodeCnts = new int[3 + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 각 방향의 인덱스들을 미리 계산해서 리스트에 저장
		precalcDirections();

		// 그리드 크기, 블리자드 시전 횟수 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 그리드 입력
		int[][] grid = new int[N][N];
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 그리드 초기 상태에 있는 구슬들을 순서대로 marbles에 담는다
		initMarbles(grid);

		// M번의 블리자드 정보 입력받고 시뮬레이션 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int direction = Integer.parseInt(st.nextToken());
			int distance = Integer.parseInt(st.nextToken());

			// 상어가 쏜다
			blizzard(direction, distance);
			
			// 구슬이 폭발한다
			repeatExploding();
			
			// 남은 구슬들이 변환된다
			change();
		}

		// 정답 계산
		int answer = 0;
		for (int marbleNum = 1; marbleNum <= 3; marbleNum++) {
			answer += (marbleNum * explodeCnts[marbleNum]);
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** 각 방향으로 일직선상에 있는 인덱스들을 리스트에 담는다 */
	public static void precalcDirections() {
		for (int i = 1, k = -1; i <= MAX_N / 2; i++) {
			k += 2 * i - 1;
			left.add(k);

			k += 2 * i;
			down.add(k);

			k += 2 * i;
			right.add(k);

			k += 2 * i;
			up.add(k);
		}

		return;
	}

	/** direction 방향으로 블리자드를 쏠 때의 인덱스들을 리턴 */
	public static List<Integer> getIndexes(int direction) {
		switch (direction) {
		case 1:
			return up;
		case 2:
			return down;
		case 3:
			return left;
		case 4:
			return right;
		default:
			return null;
		}
	}

	/** 그리드 정보로 marbles 리스트를 초기화 */
	public static void initMarbles(int[][] grid) {
		int y = N / 2, x = N / 2;

		// 안쪽에서부터 한 겹씩 수행
		for (int d = 1; d <= N / 2; d++) {
			// 좌 1칸
			x--;
			if (grid[y][x] != 0) {
				marbles.add(grid[y][x]);
			}

			// 하 (2d - 1)칸
			for (int i = 0; i < 2 * d - 1; i++) {
				y++;
				if (grid[y][x] != 0) {
					marbles.add(grid[y][x]);
				}
			}

			// 우 (2d)칸
			for (int i = 0; i < 2 * d; i++) {
				x++;
				if (grid[y][x] != 0) {
					marbles.add(grid[y][x]);
				}
			}

			// 상 (2d)칸
			for (int i = 0; i < 2 * d; i++) {
				y--;
				if (grid[y][x] != 0) {
					marbles.add(grid[y][x]);
				}
			}

			// 좌 (2d)칸
			for (int i = 0; i < 2 * d; i++) {
				x--;
				if (grid[y][x] != 0) {
					marbles.add(grid[y][x]);
				}
			}
		}

		return;
	}

	/** 상어가 블리자드를 시전하는 것을 시뮬레이션 */
	public static void blizzard(int direction, int speed) {
		List<Integer> indexes = getIndexes(direction);

		for (int i = speed - 1; i >= 0; i--) {
			int idxToRemove = indexes.get(i);
			if (idxToRemove < marbles.size()) {
				marbles.remove(idxToRemove);
			}
		}

		return;
	}

	/** 4개 이상 연속된 구슬들이 폭발하는 것을 연쇄 폭발이 그만 일어날 때까지 반복 시뮬레이션 */
	public static void repeatExploding() {
		while (tryOneExplodeIteration()) {

		}
	}

	/** 4개 이상 연속된 구슬들이 폭발하는 것을 시뮬레이션 */
	public static boolean tryOneExplodeIteration() {
		int size = marbles.size();
		if (size == 0) {
			return false;
		}

		boolean ret = false;

		// 마지막 구슬 처리
		int prev = marbles.get(size - 1);
		int cnt = 1;

		// 마지막에서 2번째 구슬부터 앞쪽 방향으로 처리
		for (int i = size - 2; i >= 0; i--) {
			int now = marbles.get(i);

			// 같은 구슬이면 카운트
			if (now == prev) {
				cnt++;
			}
			// 다른 구슬이면
			else {
				// 이전까지 쌓인 것이 4개 이상이라면 폭발
				if (cnt >= 4) {
					int marbleNum = prev;
					for (int k = cnt; k >= 1; k--) {
						explodeCnts[marbleNum]++;
						marbles.remove(i + k);
					}

					ret = true;
				}

				cnt = 1;
			}

			prev = now;
		}

		// 맨 앞 구슬 그룹 처리
		if (cnt >= 4) {
			int marbleNum = prev;
			for (int k = cnt; k >= 1; k--) {
				explodeCnts[marbleNum]++;
				marbles.remove(-1 + k);
			}

			ret = true;
		}

		return ret;
	}

	/** 구슬 그룹들이 두 개의 구슬 A, B로 변하는 것을 시뮬레이션 */
	public static void change() {
		List<Integer> changedMarbles = new ArrayList<>();

		int size = marbles.size();
		if (size == 0) {
			return;
		}

		// 첫 구슬 처리
		int cnt = 1;
		int prev = marbles.get(0);

		// 두 번째 구슬부터 처리
		for (int i = 1; i < size; i++) {
			int now = marbles.get(i);

			// 같은 그룹이면 카운팅만 한다.
			if (now == prev) {
				cnt++;
			}

			// 다른 그룹일 때는 이전 그룹의 카운팅 정보로 구슬 2개를 만들어서 changedMarbles에 넣는다.
			else {
				int A = cnt;
				int B = prev;
				changedMarbles.add(A);
				changedMarbles.add(B);

				cnt = 1;
			}

			prev = now;
		}

		// 마지막 그룹 처리
		int A = cnt;
		int B = prev;
		changedMarbles.add(A);
		changedMarbles.add(B);

		// 변환된 구슬들 중 최대 N * N - 1개까지만 담을 수 있다
		int resultSize = Math.min(changedMarbles.size(), N * N - 1);

		// marbles에 결과를 담는다
		marbles.clear();
		for (int i = 0; i < resultSize; i++) {
			marbles.add(changedMarbles.get(i));
		}

		return;
	}
}