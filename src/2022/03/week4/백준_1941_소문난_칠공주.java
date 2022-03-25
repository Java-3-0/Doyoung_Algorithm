// 47020KB, 196ms

package bj1941;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	/** 그리드 크기 */
	static final int SIZE = 5;
	/** 칠공주 인원 수 */
	static final int SEVEN = 7;
	/** 과반수 */
	static final int MAJOR = 4;

	/** 그리드 */
	static int[][] grid = new int[SIZE][SIZE];
	/** idx번 위치부터 앞으로 남은 S의 개수 */
	static int[] sLeft = new int[SIZE * SIZE];
	/** 조합 */
	static int[] combi = new int[SEVEN];
	/** 구하려는 경우의 수 */
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력을 받아서 이다솜파는 1로, 임도연파는 0으로 그리드에 저장
		for (int y = 0; y < SIZE; y++) {
			char[] line = br.readLine().toCharArray();
			for (int x = 0; x < SIZE; x++) {
				char c = line[x];
				grid[y][x] = c == 'S' ? 1 : 0;
			}
		}

		// 각 칸 위치로부터 뒤쪽으로 남은 1의 개수를 센다.
		for (int i = 0; i < SIZE * SIZE; i++) {
			if (grid[i / SIZE][i % SIZE] == 1) {
				for (int k = i; k >= 0; k--) {
					sLeft[k]++;
				}
			}
		}

		// 모든 조합 완전탐색하면서 가능한 경우의 수를 answer에 저장
		combination(0, 0, 0);

		// 출력
		System.out.println(answer);

	} // end main

	/** 모든 조합을 완전탐색하면서 경우의 수를 센다 */
	public static void combination(int hereIdx, int cnt, int cntS) {
		// 조합이 완성된 경우, 4명 이상의 S가 있고, 7명 모두가 인접한지 파악한다.
		if (cnt == SEVEN) {
			if (cntS >= MAJOR && isValid()) {
				answer++;
			}

			return;
		}

		// 조합을 완성하지 못하고 끝까지 도달한 경우 실패
		if (hereIdx == SIZE * SIZE) {
			return;
		}

		// Y가 이미 4개 이상 포함된 경우 실패
		if (cnt - cntS >= MAJOR) {
			return;
		}

		// 지금까지 넣은 S의 수와 앞으로 남은 S의 수를 더해도 4 이상 될 수 없다면 실패
		if (cntS + sLeft[hereIdx] < MAJOR) {
			return;
		}

		// 이외의 경우, 조합을 계속 만들어 본다.
		combi[cnt] = hereIdx;
		int pa = grid[hereIdx / SIZE][hereIdx % SIZE];
		if (pa == 1) {
			combination(hereIdx + 1, cnt + 1, cntS + 1);
			combination(hereIdx + 1, cnt, cntS);
		} else {
			combination(hereIdx + 1, cnt + 1, cntS);
			combination(hereIdx + 1, cnt, cntS);
		}

	}

	/** 현재 combi에 있는 모든 학생들이 인접해 있다면 true, 아니면 false를 리턴 */
	public static boolean isValid() {
		Queue<Integer> q = new LinkedList<>();
		boolean[] isVisited = new boolean[SEVEN];

		// 시작 정점
		isVisited[0] = true;
		q.offer(0);

		// bfs 수행
		int cnt = 0;
		while (!q.isEmpty()) {
			// 큐에서 하나를 꺼내서 방문
			int here = combi[q.poll()];
			cnt++;

			// 아직 방문하지 않은 정점 중 here에서 인접한 정점을 방문 체크하고 큐에 넣는다.
			for (int i = 0; i < SEVEN; i++) {
				if (!isVisited[i] && isAdjacent(here, combi[i])) {
					isVisited[i] = true;
					q.offer(i);
				}
			}
		}

		// 모두 방문했다면 성공
		if (cnt == SEVEN) {
			return true;
		}
		return false;
	}

	/** 두 점이 인접한지 여부를 리턴 */
	public static boolean isAdjacent(int idx1, int idx2) {
		int y1 = idx1 / SIZE;
		int x1 = idx1 % SIZE;

		int y2 = idx2 / SIZE;
		int x2 = idx2 % SIZE;

		int diffY = Math.abs(y2 - y1);
		int diffX = Math.abs(x2 - x1);

		if (diffY + diffX == 1) {
			return true;
		}

		return false;

	}

}