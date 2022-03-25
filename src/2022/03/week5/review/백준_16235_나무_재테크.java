// 296932KB, 1088ms

package bj16235;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10;
	static final int DIRECTIONS = 8;
	static final int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static final int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };

	/** 땅 크기 */
	static int N;
	/** 초기 나무 개수 */
	static int M;
	/** 시뮬레이션 할 연수 */
	static int K;
	/** 겨울에 양분이 추가되는 양 */
	static int[][] A;
	/** 현재 땅이 가지고 있는 양분 */
	static int[][] ground;
	/** 살아남은 트리들 */
	static List<Tree> trees = new ArrayList<Tree>();
	/** 이번 연도 봄에 죽은 나무들 */
	static List<Tree> treeDying = new ArrayList<Tree>();

	/** 나무 객체 */
	static class Tree implements Comparable<Tree> {
		int y;
		int x;
		int age;

		public Tree(int y, int x, int age) {
			super();
			this.y = y;
			this.x = x;
			this.age = age;
		}

		/** 나이 오름차순으로 나무들을 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Tree t) {
			return this.age - t.age;
		}

		@Override
		public String toString() {
			return "Tree [y=" + y + ", x=" + x + ", age=" + age + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		A = new int[N + 1][N + 1];
		ground = new int[N + 1][N + 1];

		// A[] 입력, ground[] 초기화
		for (int y = 1; y <= N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 1; x <= N; x++) {
				A[y][x] = Integer.parseInt(st.nextToken());
				ground[y][x] = 5;
			}
		}

		// 초기 나무 정보 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());
			trees.add(new Tree(y, x, age));
		}

		// K년을 시뮬레이션
		for (int year = 0; year < K; year++) {
			spring();
			summer();
			fall();
			winter();
		}

		// 살아남은 트리 개수를 센다
		int answer = trees.size();

		// 출력
		System.out.println(answer);

	} // end main

	/** 봄을 시뮬레이션한다 */
	public static void spring() {
		// 나이가 어린 나무부터 먼저 양분을 먹게 하기 위해서 정렬
		Collections.sort(trees);

		// 살아남는 나무들은 tmp에, 죽는 나무들은 treeDying에 넣는다.
		List<Tree> tmp = new ArrayList<>();
		for (Tree t : trees) {
			if (ground[t.y][t.x] < t.age) {
				treeDying.add(t);
			} else {
				ground[t.y][t.x] -= t.age;
				t.age++;
				tmp.add(t);
			}
		}

		// trees를 tmp로 갱신
		trees = tmp;

	}

	/** 여름을 시뮬레이션한다 */
	public static void summer() {
		// (죽은 나무들 나이/2) 만큼 땅에 양분을 추가
		for (Tree t : treeDying) {
			ground[t.y][t.x] += t.age / 2;
		}
		treeDying.clear();
	}

	/** 가을을 시뮬레이션한다 */
	public static void fall() {
		List<Tree> tmp = new ArrayList<>();
		for (Tree t : trees) {
			tmp.add(t);
			// 나무 나이가 5의 배수이면
			if (t.age % 5 == 0) {
				// 8방으로 나무 번식
				for (int dir = 0; dir < DIRECTIONS; dir++) {
					int ny = t.y + dy[dir];
					int nx = t.x + dx[dir];
					if (isInRange(ny, nx)) {
						tmp.add(new Tree(ny, nx, 1));
					}
				}
			}
		}

		trees = tmp;

	}

	/** 겨울을 시뮬레이션한다 */
	public static void winter() {
		// 모든 칸에 각각 A[][] 값만큼 양분 추가
		for (int y = 1; y <= N; y++) {
			for (int x = 1; x <= N; x++) {
				ground[y][x] += A[y][x];
			}
		}
	}

	/** (y, x)가 땅 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (1 <= y && y <= N && 1 <= x && x <= N) {
			return true;
		}

		return false;
	}

}