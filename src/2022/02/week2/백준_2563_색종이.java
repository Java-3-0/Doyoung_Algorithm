// 17844KB, 228ms

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int GRID_SIZE = 100;
	public static final int COLORPAPER_SIZE = 10;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		// 그리드를 만들고 0으로 초기화
		int[][] grid = new int[GRID_SIZE][GRID_SIZE];
		for (int[] arr : grid) {
			Arrays.fill(arr, 0);
		}
		
		// 색종이가 덮여지는 부분의 그리드를 1로 갱신
		for (int i = 0; i < N; i++) {
			int startX = sc.nextInt();
			int startY = sc.nextInt();
			
			for (int dy = 0; dy < COLORPAPER_SIZE; dy++) {
				for (int dx = 0; dx < COLORPAPER_SIZE; dx++) {
					grid[startY+dy][startX+dx] = 1;
				}
			}
		}
		
		// 덮여진 부분 카운트
		int count = 0;
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				count += grid[y][x];
			}
		}
		
		System.out.println(count);
	}
}
