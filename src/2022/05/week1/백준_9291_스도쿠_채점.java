// 13808KB, 108ms

package bj9291;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int GRIDSIZE = 9;

	static int[][] grid = new int[GRIDSIZE][GRIDSIZE];

	static class Position {
		int r;
		int c;
		int groupNum;

		public Position(int r, int c) {
			super();
			this.r = r;
			this.c = c;
			this.groupNum = getGroupNum(r, c);
		}

		@Override
		public String toString() {
			return "Position [r=" + r + ", c=" + c + ", groupNum=" + groupNum + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			initMemory();

			// 그리드 입력
			for (int r = 0; r < GRIDSIZE; r++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int c = 0; c < GRIDSIZE; c++) {
					int input = Integer.parseInt(st.nextToken());
					grid[r][c] = input;
				}
			}

			// 출력
			sb.append("Case ").append(tc).append(": ");

			if (isCorrect()) {
				sb.append("CORRECT");
			} else {
				sb.append("INCORRECT");
			}

			sb.append("\n");
			
			if (tc != TESTS) {
				br.readLine();
			}

		} // end for tc

		System.out.print(sb.toString());

	} // end main

	public static boolean isCorrect() {
		Set<Integer>[] rows = new HashSet[GRIDSIZE];
		Set<Integer>[] cols = new HashSet[GRIDSIZE];
		Set<Integer>[] groups = new HashSet[GRIDSIZE];

		for (int i = 0; i < GRIDSIZE; i++) {
			rows[i] = new HashSet<Integer>();
			cols[i] = new HashSet<Integer>();
			groups[i] = new HashSet<Integer>();
		}

		for (int r = 0; r < GRIDSIZE; r++) {
			for (int c = 0; c < GRIDSIZE; c++) {
				int num = grid[r][c];
				rows[r].add(num);
				cols[c].add(num);
				groups[getGroupNum(r, c)].add(num);
			}
		}

		for (int i = 0; i < GRIDSIZE; i++) {
			if (rows[i].size() != GRIDSIZE || cols[i].size() != GRIDSIZE || groups[i].size() != GRIDSIZE) {
				return false;
			}
		}

		return true;
	}

	/** 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], 0);
		}
	}

	/** 3x3 공간을 하나의 그룹으로 볼 때, 0 ~ 8사이의 그룹 번호를 리턴 */
	public static int getGroupNum(int r, int c) {
		int groupRow = r / 3;
		int groupCol = c / 3;
		int groupNum = groupRow * 3 + groupCol;
		return groupNum;
	}

}