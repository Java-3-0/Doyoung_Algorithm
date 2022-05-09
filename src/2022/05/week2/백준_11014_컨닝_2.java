// 20924KB, 476ms

package bj11014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 80, MAX_W = 80;
	static final int MAX_V = MAX_H * MAX_W;
	static final int NOT_MATCHED = -1;

	/** 함께 앉을 수 없는 방향들 */
	static final int[] dr = { -1, -1, 0, 0, 1, 1 };
	static final int[] dc = { -1, 1, -1, 1, -1, 1 };

	/** 그리드 크기 */
	static int H, W;
	/** 그리드 칸 수 */
	static int V;
	/** 그리드 */
	static int[][] grid = new int[MAX_H][MAX_W];
	/** 정의역 -> 공역으로의 간선 정보 */
	static List<Integer>[] adjList = new ArrayList[MAX_V];
	/** 공역의 각 원소를 다른 정의역과 매칭하려는 시도를 해 봤는지 여부를 나타내는 배열 */
	static boolean[] isChecked = new boolean[MAX_V];
	/** 공역의 각 원소가 정의역에 어떤 원소와 매칭되었는지를 나타내는 배열 */
	static int[] isMatchedTo = new int[MAX_V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 전역변수 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 그리드 크기 입력
			st = new StringTokenizer(br.readLine(), " ");
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			V = H * W;

			// 그리드 입력
			int cntStudents = 0;
			for (int r = 0; r < H; r++) {
				String line = br.readLine();
				for (int c = 0; c < W; c++) {
					grid[r][c] = line.charAt(c);
					if (grid[r][c] == '.') {
						cntStudents++;
					}
				}
			}

			// 함께 앉을 수 없는 학생마다 간선 정보 생성 (짝수 열 -> 홀수 열로 분리)
			for (int r = 0; r < H; r++) {
				for (int c = 0; c < W; c++) {
					if (grid[r][c] == '.') {
						int student1 = getStudentNum(r, c);
						for (int dir = 0; dir < dr.length; dir++) {
							int nextR = r + dr[dir];
							int nextC = c + dc[dir];
							if (isInRange(nextR, nextC) && grid[nextR][nextC] == '.') {
								int student2 = getStudentNum(nextR, nextC);

								if (c % 2 == 0) {
									adjList[student1].add(student2);
								}
							}
						}
					}
				}
			}

			// 간선 정보 오름차순정렬
			for (int i = 0; i < adjList.length; i++) {
				Collections.sort(adjList[i]);
			}

			// 최대 독립 집합의 수를 구한다(정점 개수 - 최대 매칭)
			int answer = cntStudents - getMaxMatches();

			// 출력용 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < adjList.length; i++) {
			adjList[i].clear();
		}
		Arrays.fill(isChecked, false);
		Arrays.fill(isMatchedTo, NOT_MATCHED);
	}

	/** 현재 adjList에서 가능한 최대 매칭 수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		for (int x = 0; x < V; x++) {
			Arrays.fill(isChecked, false);

			if (dfs(x)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 매칭 시도 */
	public static boolean dfs(int x) {
		for (int y : adjList[x]) {
			if (!isChecked[y]) {
				isChecked[y] = true;
				if (isMatchedTo[y] == NOT_MATCHED || dfs(isMatchedTo[y])) {
					isMatchedTo[y] = x;
					return true;
				}
			}
		}

		return false;
	}

	/** (r, c)에 해당하는 학생 번호를 리턴 */
	public static int getStudentNum(int r, int c) {
		return W * r + c;
	}

	/** (r, c)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int r, int c) {
		if (0 <= r && r < H && 0 <= c && c < W) {
			return true;
		}
		return false;
	}

}