// 12600KB, 96ms

package bj10026;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int[] dy = {-1, 1, 0, 0};
	public static final int[] dx = {0, 0, -1, 1};
	
	public static final int MAX_N = 100;
	public static int N;
	public static char[][] grid = new char[MAX_N][MAX_N];
	public static boolean[][] isVisited = new boolean[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		for (int y = 0; y < N; y++) {
			String s = br.readLine();
			for (int x = 0; x < N; x++) {
				grid[y][x] = s.charAt(x);
			}
		}
		br.close();
		
		// 일반적인 사람 입장에서 구역 개수 계산
		int countForNormal = countAreas();
		// 적록색맹 입장에서 구하기 위해서 녹색을 모두 적색으로 바꿈
		changeGreenToRed();
		// 적록색맹 입장에서의 구역 개수 계산
		int countForColorblind = countAreas();
		// 출력
		System.out.printf("%d %d\n", countForNormal, countForColorblind);
	}
	
	/** 그리드에 존재하는 구역의 개수를 리턴 */
	public static int countAreas() {
		// 초기화
		for (int i = 0; i < isVisited.length; i++) {
			Arrays.fill(isVisited[i], false);
		}
		int count = 0;
		
		// 모든 정점에 대해 반복
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (isVisited[y][x]) {
					continue;
				}
				
				// 새로 방문한 곳이면, 이 곳에서 인접한 같은 구역을 모두 방문
				dfs(y, x);
				// 구역 수 갱신
				count++;
			}
		}
		
		return count;
	}
	
	public static void dfs(int hereY, int hereX) {
		if (isVisited[hereY][hereX]) {
			return;
		}
		
		// 현재 정점의 방문 여부 갱신
		isVisited[hereY][hereX] = true;
		
		// 4방 탐색 가능한 인접 정점에 대해 실행
		for (int dir = 0; dir < dy.length; dir++) {
			int thereY = hereY + dy[dir];
			int thereX = hereX + dx[dir];
			
			// 범위 내이고, 현재 정점과 같은 색이면, 같은 영역이므로 dfs로 계속 탐색
			if (isInRange(thereY, thereX) && grid[thereY][thereX] == grid[hereY][hereX]) {
				dfs(thereY, thereX);
			}
		}
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= x && x < N && 0 <= y && y < N) {
			return true;
		}
		return false;
	}
	
	/** grid에서 초록색인 부분을 빨간색으로 변경 */
	public static void changeGreenToRed () {
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (grid[y][x] == 'G') {
					grid[y][x] = 'R';
				}
			}
		}
	}
}
