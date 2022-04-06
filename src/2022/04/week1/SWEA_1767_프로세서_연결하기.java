// 38948KB, 197ms

package swea1767;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	static final int INF = 987654321;
	static final int MAX_N = 12;
	static final int[] dy = { -1, 0, 1, 0 };
	static final int[] dx = { 0, 1, 0, -1 };
	static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NONE = 4;
	static final int EMPTY = 0, CORE = 1, USED = 2;

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];
	static List<Position> cores = new ArrayList<>();
	static int maxConnects;
	static int minLineLen;

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine().trim());

		// 테스트케이스 수만큼 수행
		for (int tc = 1; tc <= TESTS; tc++) {
			// 전역변수 메모리 초기화
			initMemory();

			// 그리드 크기 입력
			N = Integer.parseInt(br.readLine());

			// 그리드 입력받으면서 코어 위치 따로 리스트에 저장
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine().trim(), " ");
				for (int x = 0; x < N; x++) {
					int input = Integer.parseInt(st.nextToken());
					grid[y][x] = input;
					if (input == 1) {
						cores.add(new Position(y, x));
					}
				}
			}

			// 모든 중복순열 완전탐색하면서 maxConnects와 minLineLen 갱신
			permutation(0, 0, 0);

			// 정답을 형식에 맞게 출력 스트링빌더에 추가
			sb.append("#").append(tc).append(" ").append(minLineLen).append("\n");
		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		maxConnects = -INF;
		minLineLen = INF;
		cores.clear();
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], 0);
		}
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < N && 0 <= x && x < N) {
			return true;
		}

		return false;
	}

	/** 모든 중복순열 완전탐색하면서 maxConnects와 minLineLen 갱신. 누적 cnt와 len을 파라미터로 가져가서전달함 */
	public static void permutation(int coreIdx, int accumCnt, int accumLen) {
		// 순열이 완성된 경우
		if (coreIdx == cores.size()) {
			// 최대 연결 수와 최소 라인 길이를 갱신
			if (maxConnects < accumCnt) {
				maxConnects = accumCnt;
				minLineLen = accumLen;
			}
			else if (maxConnects == accumCnt) {
				minLineLen = Math.min(minLineLen, accumLen);
			}
			
			return;
		}
		
		// 가지치기 (지금부터 모든 코어의 연결을 성공해도, maxConnects개 이상의 연결을 얻을 수 없는 경우)
		if (accumCnt + N - coreIdx < maxConnects) {
			return;
		}

		// 사방탐색 시작 위치
		Position core = cores.get(coreIdx);
		int startY = core.y;
		int startX = core.x;

		// 사방 탐색
		OUTER: for (int dir = 0; dir < 4; dir++) {
			int len = 0;

			// 그리드 끝까지 갈 때까지 수행
			for (int y = startY + dy[dir], x = startX + dx[dir]; isInRange(y, x); y += dy[dir], x += dx[dir]) {
				// 끝까지 가지 못하고 다른 선이나 코어와 충돌했을 경우 이 탐색은 실패이므로 중단
				if (grid[y][x] != 0) {
					continue OUTER;
				}

				// 탐색이 계속되는 경우 라인 길이를 센다
				len++;
			}

			// 여기서부터는 dir 방향으로의 탐색이 그리드 끝까지 도달한 경우이다.

			// 그리드 상태를 백업해 두고, 방문한 칸들의 그리드 값을 USED로 변경한다.
			List<Integer> tmp = new ArrayList<>();
			for (int y = startY + dy[dir], x = startX + dx[dir]; isInRange(y, x); y += dy[dir], x += dx[dir]) {
				tmp.add(grid[y][x]);
				grid[y][x] = USED;
			}

			// 이렇게 변경한 그리드에서 다음 코어에 대한 재귀 호출을 수행한다
			permutation(coreIdx + 1, accumCnt + 1, accumLen + len);

			// 백업해 두었던 정보로, 그리드를 원래대로 되돌린다
			int i = 0;
			for (int y = startY + dy[dir], x = startX + dx[dir]; isInRange(y, x); y += dy[dir], x += dx[dir]) {
				grid[y][x] = tmp.get(i);
				i++;
			}
		}

		// 이 코어를 아예 연결하지 않는 경우에 대해서도 따로 처리
		permutation(coreIdx + 1, accumCnt, accumLen);
	}

}