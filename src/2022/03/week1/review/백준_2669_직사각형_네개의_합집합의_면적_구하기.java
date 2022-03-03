// 11544KB, 80ms

package baek2669;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int[][] grid = new int[101][101];
		
		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			// 평면에서 직사각형이 차지하는 부분을 채운다
			for (int dy = 0; dy < y2 - y1; dy++) {
				for (int dx = 0; dx < x2 - x1; dx++) {
					grid[x1 + dx][y1 + dy] = 1;
				}
			}
		}
		
		int answer = 0;
		for (int[] arr : grid) {
			for (int num : arr) {
				answer += num;
			}
		}
		
		System.out.println(answer);
	}

}
