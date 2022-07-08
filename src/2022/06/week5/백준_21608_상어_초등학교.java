// 27724KB, 228ms

package bj21608;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 20;
	static final int MAX_STUDENTS = MAX_N * MAX_N;
	static final int EMPTY = 0;
	static final int INF = 987654321;
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };
	static final Map<Integer, Integer> scoreMap = new HashMap<Integer, Integer>() {
		{
			put(0, 0);
			put(1, 1);
			put(2, 10);
			put(3, 100);
			put(4, 1000);
		}
	};

	static int N;
	static Set<Integer>[] likeSet = new HashSet[MAX_STUDENTS + 1];
	static int[][] grid = new int[MAX_N][MAX_N];

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Pos [y=" + y + ", x=" + x + "]";
		}

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < N && 0 <= this.x && this.x < N) {
				return true;
			}
			return false;
		}

		public boolean isEmpty() {
			if (this.getGridVal() == EMPTY) {
				return true;
			}
			return false;
		}

		public int getGridVal() {
			return grid[this.y][this.x];
		}

		public void setGridVal(int val) {
			grid[this.y][this.x] = val;
		}
	}

	static class Result implements Comparable<Result> {
		int y;
		int x;
		int cntLiking;
		int cntEmpty;

		public Result(int y, int x, int cntLiking, int cntEmpty) {
			super();
			this.y = y;
			this.x = x;
			this.cntLiking = cntLiking;
			this.cntEmpty = cntEmpty;
		}

		// 가장 우선순위가 높은 칸이 앞에 오도록 정렬하는 비교함수
		@Override
		public int compareTo(Result r) {
			if (this.cntLiking == r.cntLiking) {
				if (this.cntEmpty == r.cntEmpty) {
					if (this.y == r.y) {
						return this.x - r.x;
					}
					return this.y - r.y;
				}
				return -(this.cntEmpty - r.cntEmpty);
			}
			return -(this.cntLiking - r.cntLiking);
		}

		@Override
		public String toString() {
			return "Result [y=" + y + ", x=" + x + ", cntLiking=" + cntLiking + ", cntEmpty=" + cntEmpty + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 메모리 할당
		malloc();

		// 학생 수 입력
		N = Integer.parseInt(br.readLine());

		// 학생마다 자리 배치를 처리
		for (int i = 0; i < N * N; i++) {
			// 학생 번호 입력
			st = new StringTokenizer(br.readLine(), " ");
			int studentNum = Integer.parseInt(st.nextToken());

			// 좋아하는 학생들 입력
			for (int k = 0; k < 4; k++) {
				likeSet[studentNum].add(Integer.parseInt(st.nextToken()));
			}

			// 학생을 배치할 칸을 찾는다.
			Position target = findPositionToAssign(studentNum);

			// 그 칸에 배치한다.
			target.setGridVal(studentNum);
		}

		// 총점 계산
		int answer = getTotalScore();

		// 출력
		System.out.println(answer);

	} // end main

	/** 전역변수 중 동적 할당 필요한 것들의 메모리 할당 */
	public static void malloc() {
		// likeSet 메모리 할당
		for (int i = 0; i < likeSet.length; i++) {
			likeSet[i] = new HashSet<>(4);
		}
	}

	/** 학생 번호가 주어졌을 때, 그 학생을 배치할 위치를 리턴한다 */
	public static Position findPositionToAssign(int studentNum) {
		Result best = new Result(INF, INF, 0, 0);

		// 모든 중심 위치를 시도
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				Position center = new Position(y, x);

				// 중심 위치가 빈 칸이 아니면 넘어간다
				if (!center.isEmpty()) {
					continue;
				}

				// 중심 위치로부터 4방탐색해서 좋아하는 학생 수, 빈 칸 수를 구한다
				Result result = countAdjacent(studentNum, center);

				// 구한 결과 중 최선의 결과를 저장
				if (best.compareTo(result) > 0) {
					best = result;
				}
			}
		}

		return new Position(best.y, best.x);
	}

	/** 학생 번호와 중심 위치가 주어졌을 때, 주변에 좋아하는 학생 수와 빈 칸 수를 세어서 리턴한다 */
	public static Result countAdjacent(int studentNum, Position center) {
		int cntLiking = 0;
		int cntEmpty = 0;

		for (int dir = 0; dir < DIRECTIONS; dir++) {
			Position next = center.getNextPos(dir);

			if (!next.isInRange()) {
				continue;
			}

			int gridVal = next.getGridVal();

			if (likeSet[studentNum].contains(gridVal)) {
				cntLiking++;
			}

			if (gridVal == EMPTY) {
				cntEmpty++;
			}
		}

		return new Result(center.y, center.x, cntLiking, cntEmpty);
	}

	/** 총점을 계산해서 리턴 */
	public static int getTotalScore() {
		int ret = 0;

		// 모든 위치에서의 점수 합을 계산
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				Position center = new Position(y, x);

				ret += getScore(center);
			}
		}

		return ret;
	}

	/** 위치 하나의 점수를 계산해서 리턴 */
	public static int getScore(Position center) {
		int studentNum = center.getGridVal();
		int cnt = 0;

		for (int dir = 0; dir < DIRECTIONS; dir++) {
			Position next = center.getNextPos(dir);

			if (!next.isInRange()) {
				continue;
			}

			int gridVal = next.getGridVal();
			if (likeSet[studentNum].contains(gridVal)) {
				cnt++;
			}
		}

		return scoreMap.get(cnt);
	}

}