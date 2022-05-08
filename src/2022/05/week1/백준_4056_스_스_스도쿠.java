// 11760KB, 84ms

package bj4056;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	static final int GRIDSIZE = 9;

	static int[][] grid = new int[GRIDSIZE][GRIDSIZE];
	static Set<Integer>[] rowPossibles = new HashSet[GRIDSIZE];
	static Set<Integer>[] colPossibles = new HashSet[GRIDSIZE];
	static Set<Integer>[] groupPossibles = new HashSet[GRIDSIZE];
	static List<Position> emptyPositions = new ArrayList<>();

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
		StringBuilder sb = new StringBuilder();

		// memory allocation
		for (int i = 0; i < GRIDSIZE; i++) {
			rowPossibles[i] = new HashSet<Integer>();
			colPossibles[i] = new HashSet<Integer>();
			groupPossibles[i] = new HashSet<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			initMemory();

			for (int num = 1; num <= 9; num++) {
				for (int i = 0; i < GRIDSIZE; i++) {
					rowPossibles[i].add(num);
					colPossibles[i].add(num);
					groupPossibles[i].add(num);
				}
			}

			// 그리드 입력
			for (int r = 0; r < GRIDSIZE; r++) {
				String line = br.readLine();
				for (int c = 0; c < GRIDSIZE; c++) {
					int input = line.charAt(c) - '0';
					grid[r][c] = input;

					if (input != 0) {
						rowPossibles[r].remove(input);
						colPossibles[c].remove(input);
						groupPossibles[getGroupNum(r, c)].remove(input);
					} else {
						emptyPositions.add(new Position(r, c));
					}
				}
			}

			if (fill(0)) {
				if (isCorrect()) {
					sb.append(gridToString());
				} else {
					sb.append("Could not complete this grid.\n");
				}
			} else {
				sb.append("Could not complete this grid.\n");
			}
			
			if (tc != TESTS) {
				sb.append("\n");
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

		for (int i = 0; i < rowPossibles.length; i++) {
			rowPossibles[i].clear();
		}

		for (int i = 0; i < colPossibles.length; i++) {
			colPossibles[i].clear();
		}

		for (int i = 0; i < groupPossibles.length; i++) {
			groupPossibles[i].clear();
		}

		emptyPositions.clear();
	}

	/** 그리드를 출력 */
	public static String gridToString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < GRIDSIZE; y++) {
			for (int x = 0; x < GRIDSIZE; x++) {
				sb.append(grid[y][x]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	/** idx번 빈 칸부터 채우는 것을 시도, 성공 시 true, 실패 시 false를 리턴 */
	public static boolean fill(int idx) {
		// 다 채운 경우
		if (idx == emptyPositions.size()) {
			return true;
		}

		Position position = emptyPositions.get(idx);
		int r = position.r;
		int c = position.c;
		int g = position.groupNum;

		// 가능한 모든 수를 시도
		for (int num = 1; num <= 9; num++) {
			// row, cow, group 모두에서 가능한 숫자면 채워본다
			if (isPossible(r, c, g, num)) {
				// 그리드와 가능한 숫자들을 갱신하고
				grid[r][c] = num;
				rowPossibles[r].remove(num);
				colPossibles[c].remove(num);
				groupPossibles[g].remove(num);

				// 다음 빈 칸을 채우러 간다
				if (fill(idx + 1)) {
					return true;
				}

				// 갱신했던 것들을 되돌린다
				grid[r][c] = 0;
				rowPossibles[r].add(num);
				colPossibles[c].add(num);
				groupPossibles[g].add(num);
			}
		}

		return false;
	}

	/** 3x3 공간을 하나의 그룹으로 볼 때, 0 ~ 8사이의 그룹 번호를 리턴 */
	public static int getGroupNum(int r, int c) {
		int groupRow = r / 3;
		int groupCol = c / 3;
		int groupNum = groupRow * 3 + groupCol;
		return groupNum;
	}

	/** 그룹 번호가 g인 (r, c)위치에 num을 채우는 것이 가능한지 여부를 리턴 */
	public static boolean isPossible(int r, int c, int g, int num) {
		if (rowPossibles[r].contains(num) && colPossibles[c].contains(num) && groupPossibles[g].contains(num)) {
			return true;
		}
		return false;
	}

}