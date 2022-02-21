// 14200KB, 112ms

package bj1025;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_SIZE = 9;
	public static int height, width;
	public static int[][] grid = new int[MAX_SIZE][MAX_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				grid[y][x] = line.charAt(x) - '0';
			}
		}

		// 가장 큰 완전제곱수 계산
		int answer = -1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				answer = Math.max(answer, maxPerfectSquare(y, x));
			}
		}
		
		// 출력
		System.out.println(answer);
	}

	/** (startY, startX) 위치에서 시작해서 만들 수 있는 가장 큰 완전제곱수를 리턴 */
	public static int maxPerfectSquare(int startY, int startX) {
		int ret = -1;
		
		// 시작 지점 처리
		if (isPerfectSquare(grid[startY][startX])) {
			ret = Math.max(ret, grid[startY][startX]);
		}

		// 모든 행, 열 증가량에 대해 완전탐색
		for (int dy = -height + 1; dy <= height - 1; dy++) {
			for (int dx = -width + 1; dx < width; dx++) {
				if (dy == 0 && dx == 0) {
					continue;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(grid[startY][startX]);
				
				int y = startY + dy;
				int x = startX + dx;
				while (0 <= y && y < height && 0 <= x && x < width) {				
					sb.append(grid[y][x]);
					int num = Integer.parseInt(sb.toString());
					
					if (isPerfectSquare(num)) {
						ret = Math.max(ret, num);
					}
					
					y += dy;
					x += dx;
				}
			}
		}
		
		return ret;
	}
	
	/** 파라미터로 주어진 정수가 완전제곱수이면 true, 아니면 false를 리턴 */
	public static boolean isPerfectSquare(int n) {
		int sqrt = (int) Math.sqrt(n);
		if (sqrt * sqrt == n) {
			return true;
		}
		return false;
	}
}