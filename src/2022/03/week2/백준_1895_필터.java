// 12688KB, 96ms

package bj1895;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_H = 40, MAX_W = 40;
	
	static int H, W;
	static int[][] grid = new int[MAX_H][MAX_W];
	static int T;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// H, W 입력
		st = new StringTokenizer(br.readLine(), " ");
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		// 그리드 입력
		for (int y = 0; y < H; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < W; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		// T 입력
		T = Integer.parseInt(br.readLine());

		// 각 위치에서 중간값을 구하고 T와 비교해서 카운트한다.
		int cnt = 0;
		for (int startY = 0; startY <= H - 3; startY++) {
			for (int startX = 0; startX <= W - 3; startX++) {
				int median = getMedian(startY, startX);
				if (T <= median) {
					cnt++;
				}
			}
		}
		
		// 출력
		System.out.println(cnt);
		
	}
	
	/** (startY, startX) 위치를 좌상단으로 한 3x3 영역의 중간값을 리턴 */
	public static int getMedian(int startY, int startX) {
		int len = 3 * 3;
		int[] arr = new int[len];
		for (int dy = 0; dy < 3; dy++) {
			for (int dx = 0; dx < 3; dx++) {
				int y = startY + dy;
				int x = startX + dx;
				arr[3 * dy + dx] = grid[y][x];
			}
		}
		
		Arrays.sort(arr);
		
		int median = arr[len / 2];
		
		return median;
	}

}